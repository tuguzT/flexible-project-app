package io.github.tuguzt.flexibleproject.viewmodel.basic.project.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.project.Project
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectData
import io.github.tuguzt.flexibleproject.domain.model.project.Visibility
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceFilters
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceIdFilters
import io.github.tuguzt.flexibleproject.domain.usecase.project.CreateProject
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.FilterWorkspaces
import io.github.tuguzt.flexibleproject.viewmodel.StoreProvider
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.AddProjectStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.AddProjectStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.AddProjectStore.State
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AddProjectStoreProvider(
    private val create: CreateProject,
    private val workspaces: FilterWorkspaces,
    private val storeFactory: StoreFactory,
    private val coroutineContext: CoroutineContext,
) : StoreProvider<Intent, State, Label> {
    override fun provide(): AddProjectStore =
        object :
            AddProjectStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = AddProjectStore::class.simpleName,
                initialState = State(
                    name = "",
                    description = "",
                    visibility = Visibility.Public,
                    allWorkspaces = listOf(),
                    workspace = null,
                    loading = true,
                    valid = false,
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Message {
        object Loading : Message
        data class AllWorkspacesLoaded(val allWorkspaces: List<Workspace>) : Message
        data class NameChanged(val name: String, val valid: Boolean) : Message
        data class DescriptionChanged(val description: String) : Message
        data class VisibilityChanged(val visibility: Visibility) : Message
        data class WorkspaceChanged(val workspace: Workspace?, val valid: Boolean) : Message
        data class ProjectCreated(val project: Project) : Message
        object Error : Message
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Message, Label>(
        mainContext = coroutineContext,
    ) {
        override fun executeAction(action: Unit, getState: () -> State) {
            dispatch(Message.Loading)
            scope.launch {
                val filters = WorkspaceFilters()
                val workspacesFlow = when (val result = workspaces.workspaces((filters))) {
                    is Result.Success -> result.data
                    is Result.Error -> {
                        dispatch(Message.Error)
                        when (val error = result.error) {
                            is FilterWorkspaces.Exception.Repository -> when (error.error) {
                                is BaseException.LocalStore -> publish(Label.LocalStoreError)
                                is BaseException.NetworkAccess -> publish(Label.NetworkAccessError)
                                is BaseException.Unknown -> publish(Label.UnknownError)
                            }
                        }
                        return@launch
                    }
                }
                workspacesFlow.collect { workspaces ->
                    dispatch(Message.AllWorkspacesLoaded(workspaces))
                }
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.ChangeName -> changeName(intent.name, getState())
                is Intent.ChangeDescription -> changeDescription(intent.description)
                is Intent.ChangeVisibility -> changeVisibility(intent.visibility)
                is Intent.ChangeWorkspace -> changeWorkspace(intent.workspace, getState())
                is Intent.ChangeWorkspaceFromId -> changeWorkspaceFromId(intent.id, getState())
                Intent.CreateProject -> createProject(state = getState())
            }

        private fun changeName(name: String, state: State) {
            val valid = name.isNotBlank() && state.workspace != null
            dispatch(Message.NameChanged(name, valid))
        }

        private fun changeDescription(description: String) {
            dispatch(Message.DescriptionChanged(description))
        }

        private fun changeVisibility(visibility: Visibility) {
            dispatch(Message.VisibilityChanged(visibility))
        }

        private fun changeWorkspace(workspace: Workspace?, state: State) {
            val valid = workspace != null && state.name.isNotBlank()
            dispatch(Message.WorkspaceChanged(workspace, valid))
        }

        private fun changeWorkspaceFromId(id: WorkspaceId, state: State) {
            dispatch(Message.Loading)
            scope.launch {
                val filters = WorkspaceFilters(id = WorkspaceIdFilters(eq = Equal(id)))
                val workspace = when (val result = workspaces.workspaces((filters))) {
                    is Result.Success -> result.data.firstOrNull()?.firstOrNull()
                    is Result.Error -> {
                        dispatch(Message.Error)
                        when (val error = result.error) {
                            is FilterWorkspaces.Exception.Repository -> when (error.error) {
                                is BaseException.LocalStore -> publish(Label.LocalStoreError)
                                is BaseException.NetworkAccess -> publish(Label.NetworkAccessError)
                                is BaseException.Unknown -> publish(Label.UnknownError)
                            }
                        }
                        return@launch
                    }
                }
                changeWorkspace(workspace, state)
            }
        }

        private fun createProject(state: State) {
            dispatch(Message.Loading)
            val workspace = state.workspace ?: run {
                dispatch(Message.Error)
                publish(Label.UnknownError)
                return
            }
            scope.launch {
                val data = ProjectData(
                    workspace = workspace.id,
                    name = state.name.trim(),
                    description = state.description,
                    visibility = state.visibility,
                    image = null,
                )
                when (val result = create.create(data)) {
                    is Result.Success -> {
                        val project = result.data
                        dispatch(Message.ProjectCreated(project))
                        publish(Label.ProjectCreated(project))
                    }

                    is Result.Error -> {
                        dispatch(Message.Error)
                        when (val error = result.error) {
                            is CreateProject.Exception.NoWorkspace -> publish(Label.UnknownError)
                            is CreateProject.Exception.Repository -> when (error.error) {
                                is BaseException.LocalStore -> publish(Label.LocalStoreError)
                                is BaseException.NetworkAccess -> publish(Label.NetworkAccessError)
                                is BaseException.Unknown -> publish(Label.UnknownError)
                            }
                        }
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                Message.Loading -> copy(loading = true)
                is Message.NameChanged -> copy(name = msg.name, valid = msg.valid)
                is Message.DescriptionChanged -> copy(description = msg.description)
                is Message.VisibilityChanged -> copy(visibility = msg.visibility)
                is Message.ProjectCreated -> copy(loading = false)
                Message.Error -> copy(loading = false)
                is Message.WorkspaceChanged -> copy(
                    workspace = msg.workspace,
                    valid = msg.valid,
                    loading = false,
                )

                is Message.AllWorkspacesLoaded -> copy(
                    allWorkspaces = msg.allWorkspaces,
                    loading = false,
                )
            }
    }
}
