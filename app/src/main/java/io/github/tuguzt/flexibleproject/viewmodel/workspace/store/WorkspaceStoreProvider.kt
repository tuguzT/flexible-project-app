package io.github.tuguzt.flexibleproject.viewmodel.workspace.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.DeleteWorkspace
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.FindWorkspaceById
import io.github.tuguzt.flexibleproject.viewmodel.StoreProvider
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore.State
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WorkspaceStoreProvider(
    private val findById: FindWorkspaceById,
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
        data class Loaded(val workspace: Workspace) : Message
        data class NotFound(val id: WorkspaceId) : Message
        data class WorkspaceDeleted(val workspace: Workspace) : Message
        object Error : Message
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Unit, State, Message, Label>(mainContext = coroutineContext) {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.Load -> load(intent.id)
                Intent.Delete -> delete(state = getState())
            }

        private fun load(id: WorkspaceId) {
            dispatch(Message.Loading)
            scope.launch {
                when (val result = findById.findById(id)) {
                    is Result.Success -> dispatch(Message.Loaded(result.data))
                    is Result.Error -> {
                        dispatch(Message.Error)
                        when (val error = result.error) {
                            is FindWorkspaceById.Exception.NoWorkspace -> {
                                dispatch(Message.NotFound(id))
                                publish(Label.NotFound(id))
                            }

                            is FindWorkspaceById.Exception.Repository -> when (error.error) {
                                is BaseException.LocalStore -> publish(Label.LocalStoreError)
                                is BaseException.NetworkAccess -> publish(Label.NetworkAccessError)
                                is BaseException.Unknown -> publish(Label.UnknownError)
                            }
                        }
                    }
                }
            }
        }

        private fun delete(state: State) {
            dispatch(Message.Loading)

            val workspace = state.workspace ?: error("Workspace must be present")
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
