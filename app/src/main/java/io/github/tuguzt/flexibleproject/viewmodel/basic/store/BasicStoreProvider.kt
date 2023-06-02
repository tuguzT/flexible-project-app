package io.github.tuguzt.flexibleproject.viewmodel.basic.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceFilters
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.FilterWorkspaces
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.WorkspacesFlow
import io.github.tuguzt.flexibleproject.viewmodel.StoreProvider
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStore.State
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BasicStoreProvider(
    private val allFlow: WorkspacesFlow,
    private val workspaces: FilterWorkspaces,
    private val storeFactory: StoreFactory,
    private val coroutineContext: CoroutineContext,
) : StoreProvider<Intent, State, Label> {
    override fun provide(): BasicStore =
        object :
            BasicStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = BasicStore::class.simpleName,
                initialState = State(
                    workspaces = listOf(),
                    workspacesExpanded = true,
                    loading = true,
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Message {
        object Loading : Message
        data class Loaded(val workspaces: List<Workspace>) : Message
        data class WorkspacesExpand(val expanded: Boolean) : Message
        object Error : Message
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Unit, State, Message, Label>(mainContext = coroutineContext) {
        override fun executeAction(action: Unit, getState: () -> State) {
            scope.launch {
                val workspacesFlow = when (val result = allFlow.workspacesFlow()) {
                    is Result.Success -> result.data
                    is Result.Error -> {
                        dispatch(Message.Error)
                        when (val error = result.error) {
                            is WorkspacesFlow.Exception.Repository -> when (error.error) {
                                is BaseException.LocalStore -> publish(Label.LocalStoreError)
                                is BaseException.NetworkAccess -> publish(Label.NetworkAccessError)
                                is BaseException.Unknown -> publish(Label.UnknownError)
                            }
                        }
                        return@launch
                    }
                }
                workspacesFlow.collect { workspaces ->
                    dispatch(Message.Loaded(workspaces))
                }
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.Load -> load()
                is Intent.WorkspacesExpand -> expandWorkspaces(intent.expanded)
            }

        private fun load() {
            dispatch(Message.Loading)
            scope.launch {
                when (val result = workspaces.workspaces(WorkspaceFilters())) {
                    is Result.Success -> dispatch(Message.Loaded(result.data))
                    is Result.Error -> {
                        dispatch(Message.Error)
                        when (val error = result.error) {
                            is FilterWorkspaces.Exception.Repository -> when (error.error) {
                                is BaseException.LocalStore -> publish(Label.LocalStoreError)
                                is BaseException.NetworkAccess -> publish(Label.NetworkAccessError)
                                is BaseException.Unknown -> publish(Label.UnknownError)
                            }
                        }
                    }
                }
            }
        }

        private fun expandWorkspaces(expanded: Boolean) {
            dispatch(Message.WorkspacesExpand(expanded))
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                Message.Loading -> copy(loading = true)
                is Message.Loaded -> copy(workspaces = msg.workspaces, loading = false)
                is Message.WorkspacesExpand -> copy(workspacesExpanded = msg.expanded)
                Message.Error -> copy(loading = false)
            }
    }
}
