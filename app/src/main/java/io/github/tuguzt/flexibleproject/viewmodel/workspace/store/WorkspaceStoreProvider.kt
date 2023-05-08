package io.github.tuguzt.flexibleproject.viewmodel.workspace.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.FindWorkspaceById
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore.State
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WorkspaceStoreProvider(
    private val findById: FindWorkspaceById,
    private val storeFactory: StoreFactory,
    private val coroutineContext: CoroutineContext,
) {
    fun provide(): WorkspaceStore =
        object :
            WorkspaceStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = WorkspaceStore::class.simpleName,
                initialState = State(workspace = null, isLoading = true),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Message {
        object Loading : Message
        data class Loaded(val workspace: Workspace) : Message
        data class NotFound(val id: WorkspaceId) : Message
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Unit, State, Message, Label>(mainContext = coroutineContext) {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.Load -> load(intent.id)
            }

        private fun load(id: WorkspaceId) {
            dispatch(Message.Loading)
            scope.launch {
                val workspace = findById.findById(id)
                if (workspace == null) {
                    dispatch(Message.NotFound(id))
                    publish(Label.NotFound(id))
                    return@launch
                }
                dispatch(Message.Loaded(workspace))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                Message.Loading -> copy(isLoading = true)
                is Message.Loaded -> copy(workspace = msg.workspace, isLoading = false)
                is Message.NotFound -> copy(workspace = null, isLoading = false)
            }
    }
}
