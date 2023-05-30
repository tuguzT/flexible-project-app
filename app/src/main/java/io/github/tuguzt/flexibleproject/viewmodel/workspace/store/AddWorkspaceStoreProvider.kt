package io.github.tuguzt.flexibleproject.viewmodel.workspace.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceData
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.CreateWorkspace
import io.github.tuguzt.flexibleproject.viewmodel.StoreProvider
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.AddWorkspaceStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.AddWorkspaceStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.AddWorkspaceStore.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration.Companion.seconds

class AddWorkspaceStoreProvider(
    private val create: CreateWorkspace,
    private val storeFactory: StoreFactory,
    private val coroutineContext: CoroutineContext,
) : StoreProvider<Intent, State, Label> {
    override fun provide(): AddWorkspaceStore =
        object :
            AddWorkspaceStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = AddWorkspaceStore::class.simpleName,
                initialState = State(
                    name = "",
                    description = "",
                    visibility = Visibility.Public,
                    loading = false,
                    valid = false,
                ),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Message {
        data class NameChanged(val name: String, val valid: Boolean) : Message
        data class DescriptionChanged(val description: String) : Message
        data class VisibilityChanged(val visibility: Visibility) : Message
        object Loading : Message
        data class WorkspaceCreated(val id: WorkspaceId) : Message
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Unit, State, Message, Label>(mainContext = coroutineContext) {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.ChangeName -> changeName(intent.name)
                is Intent.ChangeDescription -> changeDescription(intent.description)
                is Intent.ChangeVisibility -> changeVisibility(intent.visibility)
                Intent.CreateWorkspace -> createWorkspace(state = getState())
            }

        private fun changeName(name: String) {
            val valid = name.isNotBlank()
            dispatch(Message.NameChanged(name, valid))
        }

        private fun changeDescription(description: String) {
            dispatch(Message.DescriptionChanged(description))
        }

        private fun changeVisibility(visibility: Visibility) {
            dispatch(Message.VisibilityChanged(visibility))
        }

        private fun createWorkspace(state: State) {
            dispatch(Message.Loading)
            scope.launch {
                delay(2.seconds)
                val data = WorkspaceData(
                    name = state.name.trim(),
                    description = state.description,
                    visibility = state.visibility,
                    imageUrl = null,
                )
                val id = create.create(data)
                dispatch(Message.WorkspaceCreated(id))
                publish(Label.WorkspaceCreated(id))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                is Message.NameChanged -> copy(name = msg.name, valid = msg.valid)
                is Message.DescriptionChanged -> copy(description = msg.description)
                is Message.VisibilityChanged -> copy(visibility = msg.visibility)
                Message.Loading -> copy(loading = true)
                is Message.WorkspaceCreated -> copy(loading = false)
            }
    }
}
