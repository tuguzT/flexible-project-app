package io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectDataFilters
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectFilters
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceFilters
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceIdFilters
import io.github.tuguzt.flexibleproject.domain.usecase.project.FilterProjects
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.DeleteWorkspace
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.FilterWorkspaces
import io.github.tuguzt.flexibleproject.viewmodel.StoreProvider
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.WorkspaceStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.WorkspaceStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.WorkspaceStore.State
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.WorkspaceStore.State.WorkspaceWithProjects
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WorkspaceStoreProvider(
    private val workspaces: FilterWorkspaces,
    private val projects: FilterProjects,
    private val delete: DeleteWorkspace,
    private val storeFactory: StoreFactory,
    private val coroutineContext: CoroutineContext,
) : StoreProvider<Intent, State, Label> {
    override fun provide(): WorkspaceStore =
        object :
            WorkspaceStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = WorkspaceStore::class.simpleName,
                initialState = State(workspace = null, loading = true),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Message {
        object Loading : Message
        data class Loaded(val workspace: WorkspaceWithProjects) : Message
        data class NotFound(val id: WorkspaceId) : Message
        data class WorkspaceDeleted(val workspace: Workspace) : Message
        object Error : Message
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Message, Label>(
        mainContext = coroutineContext,
    ) {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.Load -> load(intent.id)
                Intent.Delete -> delete(state = getState())
            }

        private fun load(id: WorkspaceId) {
            dispatch(Message.Loading)
            scope.launch {
                val filters = WorkspaceFilters(id = WorkspaceIdFilters(eq = Equal(id)))
                val workspaceFlow = when (val result = workspaces.workspaces(filters)) {
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
                workspaceFlow.collectLatest { workspaces ->
                    val workspace = workspaces.firstOrNull()
                    if (workspace == null) {
                        dispatch(Message.NotFound(id))
                        publish(Label.NotFound(id))
                        return@collectLatest
                    }
                    val projectFilters = run {
                        val workspaceFilters = WorkspaceIdFilters(eq = Equal(workspace.id))
                        ProjectFilters(data = ProjectDataFilters(workspace = workspaceFilters))
                    }
                    val projectsFlow = when (val result = projects.projects(projectFilters)) {
                        is Result.Success -> result.data
                        is Result.Error -> {
                            dispatch(Message.Error)
                            when (val error = result.error) {
                                is FilterProjects.Exception.Repository -> when (error.error) {
                                    is BaseException.LocalStore -> publish(Label.LocalStoreError)
                                    is BaseException.NetworkAccess -> publish(Label.NetworkAccessError)
                                    is BaseException.Unknown -> publish(Label.UnknownError)
                                }
                            }
                            return@collectLatest
                        }
                    }
                    // FIXME should update on each incoming list, but idk how to pass it to UI
                    val projects = projectsFlow.firstOrNull() ?: return@collectLatest
                    val workspaceWithProjects = WorkspaceWithProjects(workspace, projects)
                    dispatch(Message.Loaded(workspaceWithProjects))
                }
            }
        }

        private fun delete(state: State) {
            dispatch(Message.Loading)

            val workspace = state.workspace?.workspace ?: TODO("Workspace must be present")
            scope.launch {
                when (val result = delete.delete(workspace.id)) {
                    is Result.Success -> {
                        @Suppress("NAME_SHADOWING")
                        val workspace = result.data
                        dispatch(Message.WorkspaceDeleted(workspace))
                        publish(Label.WorkspaceDeleted(workspace))
                    }

                    is Result.Error -> {
                        dispatch(Message.Error)
                        when (val error = result.error) {
                            is DeleteWorkspace.Exception.NoWorkspace -> {
                                dispatch(Message.NotFound(workspace.id))
                                publish(Label.NotFound(workspace.id))
                            }

                            is DeleteWorkspace.Exception.Repository -> when (error.error) {
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
                is Message.Loaded -> copy(workspace = msg.workspace, loading = false)
                is Message.NotFound -> copy(workspace = null, loading = false)
                is Message.WorkspaceDeleted -> copy(workspace = null, loading = false)
                Message.Error -> copy(loading = false)
            }
    }
}
