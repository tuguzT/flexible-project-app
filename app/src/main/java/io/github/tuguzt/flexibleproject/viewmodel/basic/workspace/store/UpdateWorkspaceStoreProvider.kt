package io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store

import android.util.Patterns
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.workspace.Description
import io.github.tuguzt.flexibleproject.domain.model.workspace.Image
import io.github.tuguzt.flexibleproject.domain.model.workspace.Name
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceFilters
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceIdFilters
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.FilterWorkspaces
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.UpdateWorkspace
import io.github.tuguzt.flexibleproject.viewmodel.StoreProvider
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.UpdateWorkspaceStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.UpdateWorkspaceStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.UpdateWorkspaceStore.State
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import io.github.tuguzt.flexibleproject.domain.model.workspace.UpdateWorkspace as UpdateWorkspaceInput

class UpdateWorkspaceStoreProvider(
    private val workspaces: FilterWorkspaces,
    private val update: UpdateWorkspace,
    private val storeFactory: StoreFactory,
    private val coroutineContext: CoroutineContext,
) : StoreProvider<Intent, State, Label> {
    override fun provide(): UpdateWorkspaceStore =
        object :
            UpdateWorkspaceStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = UpdateWorkspaceStore::class.simpleName,
                initialState = State(
                    name = "",
                    description = "",
                    visibility = Visibility.Public,
                    image = null,
                    valid = false,
                    loading = true,
                ),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Message {
        object Loading : Message
        data class Loaded(val workspace: Workspace) : Message
        data class NotFound(val id: WorkspaceId) : Message
        data class NameChanged(val name: Name, val valid: Boolean) : Message
        data class DescriptionChanged(val description: Description, val valid: Boolean) : Message
        data class VisibilityUpdated(val visibility: Visibility, val valid: Boolean) : Message
        data class ImageChanged(val image: Image?, val valid: Boolean) : Message
        object WorkspaceUpdated : Message
        object Error : Message
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Message, Label>(
        mainContext = coroutineContext,
    ) {
        private var loadedWorkspace: Workspace? = null

        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.Load -> load(intent.id)
                is Intent.ChangeName -> changeName(intent.name, getState())
                is Intent.ChangeDescription -> changeDescription(intent.description, getState())
                is Intent.ChangeVisibility -> changeVisibility(intent.visibility, getState())
                is Intent.ChangeImage -> changeImage(intent.image, getState())
                Intent.UpdateWorkspace -> updateWorkspace(state = getState())
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
                workspaceFlow.collect { workspaces ->
                    val workspace = workspaces.firstOrNull()
                    if (workspace == null) {
                        dispatch(Message.NotFound(id))
                        publish(Label.NotFound(id))
                        return@collect
                    }
                    loadedWorkspace = workspace
                    dispatch(Message.Loaded(workspace))
                }
            }
        }

        private fun changeName(name: Name, state: State) {
            val valid = run {
                val workspace = loadedWorkspace?.data ?: return@run false
                checkIfValid(name, state.image) && name.trim() != workspace.name
            }
            dispatch(Message.NameChanged(name, valid))
        }

        private fun changeDescription(description: Description, state: State) {
            val valid = run {
                val workspace = loadedWorkspace?.data ?: return@run false
                checkIfValid(state.name, state.image) && description.trim() != workspace.description
            }
            dispatch(Message.DescriptionChanged(description, valid))
        }

        private fun changeVisibility(visibility: Visibility, state: State) {
            val valid = run {
                val workspace = loadedWorkspace?.data ?: return@run false
                checkIfValid(state.name, state.image) && visibility != workspace.visibility
            }
            dispatch(Message.VisibilityUpdated(visibility, valid))
        }

        private fun changeImage(image: Image?, state: State) {
            val valid = run {
                val workspace = loadedWorkspace?.data ?: return@run false
                checkIfValid(state.name, image) && image?.trim() != workspace.image
            }
            dispatch(Message.ImageChanged(image, valid))
        }

        private fun checkIfValid(
            name: Name,
            image: Image?,
        ): Boolean =
            name.isNotBlank() &&
                image?.trim()?.let { Patterns.WEB_URL.matcher(it).matches() } ?: true

        private fun updateWorkspace(state: State) {
            dispatch(Message.Loading)
            scope.launch {
                val workspace = loadedWorkspace ?: run {
                    return@launch
                }
                val updateInput = UpdateWorkspaceInput(
                    name = state.name.takeIf { it != workspace.data.name },
                    description = state.description.takeIf { it != workspace.data.description },
                    visibility = state.visibility.takeIf { it != workspace.data.visibility },
                    image = state.image.takeIf { it != workspace.data.image },
                )
                when (val result = update.update(workspace.id, updateInput)) {
                    is Result.Success -> {
                        dispatch(Message.WorkspaceUpdated)
                        publish(Label.WorkspaceUpdated)
                    }

                    is Result.Error -> {
                        dispatch(Message.Error)
                        when (val error = result.error) {
                            is UpdateWorkspace.Exception.NoWorkspace -> {
                                dispatch(Message.NotFound(workspace.id))
                                publish(Label.NotFound(workspace.id))
                            }

                            is UpdateWorkspace.Exception.Repository -> when (error.error) {
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
                is Message.Loaded -> copy(
                    name = msg.workspace.data.name,
                    description = msg.workspace.data.description,
                    visibility = msg.workspace.data.visibility,
                    image = msg.workspace.data.image,
                    valid = false,
                    loading = false,
                )

                is Message.NotFound -> copy(valid = false, loading = false)
                is Message.NameChanged -> copy(name = msg.name, valid = msg.valid)
                is Message.VisibilityUpdated -> copy(visibility = msg.visibility, valid = msg.valid)
                is Message.ImageChanged -> copy(image = msg.image, valid = msg.valid)
                is Message.DescriptionChanged -> copy(
                    description = msg.description,
                    valid = msg.valid,
                )

                Message.WorkspaceUpdated -> copy(loading = false)
                Message.Error -> copy(loading = false)
            }
    }
}
