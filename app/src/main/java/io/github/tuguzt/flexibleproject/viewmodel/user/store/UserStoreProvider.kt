package io.github.tuguzt.flexibleproject.viewmodel.user.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.Id
import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration.Companion.seconds

class UserStoreProvider(
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
        object Loaded : Message
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Unit, State, Message, Label>(mainContext = coroutineContext) {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                Intent.Load -> load()
            }

        private fun load() {
            scope.launch {
                delay(3.seconds)
                dispatch(Message.Loaded)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                Message.Loaded -> copy(
                    user = User(
                        id = Id("user"),
                        data = UserData(
                            name = "tuguzT",
                            displayName = "Timur Tugushev",
                            role = Role.User,
                            email = "timurka.tugushev@gmail.com",
                            avatarUrl = null,
                        ),
                    ),
                    isLoading = false,
                )
            }
    }
}
