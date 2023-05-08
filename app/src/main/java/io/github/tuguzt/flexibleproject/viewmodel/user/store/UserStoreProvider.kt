package io.github.tuguzt.flexibleproject.viewmodel.user.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.domain.usecase.user.ReadUser
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.State
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserStoreProvider(
    private val readUser: ReadUser,
    private val storeFactory: StoreFactory,
    private val coroutineContext: CoroutineContext,
) {
    fun provide(): UserStore =
        object :
            UserStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = UserStore::class.simpleName,
                initialState = State(user = null, isLoading = true),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Message {
        object Loading : Message
        data class Loaded(val user: User) : Message
        data class NoUser(val id: UserId) : Message
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
                when (val user = readUser.readUser(id)) {
                    null -> dispatch(Message.NoUser(id))
                    else -> dispatch(Message.Loaded(user))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                Message.Loading -> copy(isLoading = true)
                is Message.Loaded -> copy(user = msg.user, isLoading = false)
                is Message.NoUser -> copy(user = null, isLoading = false)
            }
    }
}
