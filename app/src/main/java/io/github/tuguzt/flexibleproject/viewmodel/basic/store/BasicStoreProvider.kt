package io.github.tuguzt.flexibleproject.viewmodel.basic.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.ReadAllWorkspaces
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStore.State
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BasicStoreProvider(
    private val readAll: ReadAllWorkspaces,
    private val storeFactory: StoreFactory,
    private val coroutineContext: CoroutineContext,
) {
    fun provide(): BasicStore =
        object :
            BasicStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = BasicStore::class.simpleName,
                initialState = State(workspaces = listOf(), isLoading = true),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Message {
        object Loading : Message
        data class Loaded(val workspaces: List<Workspace>) : Message
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Unit, State, Message, Label>(mainContext = coroutineContext) {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.Load -> load()
            }

        private fun load() {
            dispatch(Message.Loading)
            scope.launch {
                val workspace = readAll.readAll()
                dispatch(Message.Loaded(workspace))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                Message.Loading -> copy(isLoading = true)
                is Message.Loaded -> copy(workspaces = msg.workspaces, isLoading = false)
            }
    }
}
