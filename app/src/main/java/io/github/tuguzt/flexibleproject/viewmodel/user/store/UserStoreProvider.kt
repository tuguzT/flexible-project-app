package io.github.tuguzt.flexibleproject.viewmodel.user.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.domain.usecase.user.FindUserById
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.State
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserStoreProvider(
    private val findById: FindUserById,
    private val storeFactory: StoreFactory,
    private val coroutineContext: CoroutineContext,
) {
    fun provide(): UserStore =
        object :
            UserStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = UserStore::class.simpleName,
                initialState = State(user = null, loading = true),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Message {
        object Loading : Message
        data class Loaded(val user: User) : Message
        data class NotFound(val id: UserId) : Message
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Unit, State, Message, Label>(mainContext = coroutineContext) {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.Load -> load(intent.id)
            }

        private fun load(id: UserId) {
            dispatch(Message.Loading)
            scope.launch {
                val user = findById.findById(id)
                if (user == null) {
                    dispatch(Message.NotFound(id))
                    publish(Label.NotFound(id))
                    return@launch
                }
                dispatch(Message.Loaded(user))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                Message.Loading -> copy(loading = true)
                is Message.Loaded -> copy(user = msg.user, loading = false)
                is Message.NotFound -> copy(user = null, loading = false)
            }
    }
}
