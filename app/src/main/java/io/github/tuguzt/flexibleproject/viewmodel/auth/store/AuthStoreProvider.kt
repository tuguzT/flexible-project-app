package io.github.tuguzt.flexibleproject.viewmodel.auth.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.user.UserCredentials
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStore.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration.Companion.seconds

class AuthStoreProvider(
    private val storeFactory: StoreFactory,
    private val coroutineContext: CoroutineContext,
) {
    fun provide(): AuthStore =
        object :
            AuthStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = AuthStore::class.simpleName,
                initialState = State(currentUser = null, loading = false),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Message {
        object Loading : Message
        object LoggedOut : Message
        data class SignedIn(val id: UserId) : Message
        object SomeError : Message
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Unit, State, Message, Label>(mainContext = coroutineContext) {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                Intent.LogOut -> logOut()
                is Intent.SignIn -> signIn(intent.credentials)
                is Intent.SignUp -> signUp(intent.credentials)
            }

        private fun logOut() {
            dispatch(Message.Loading)
            scope.launch {
                delay(2.seconds)
                dispatch(Message.LoggedOut)
            }
        }

        private fun signIn(credentials: UserCredentials) {
            dispatch(Message.Loading)
            scope.launch {
                delay(2.seconds)
                val (name, _) = credentials
                if (name != "tuguzT") {
                    dispatch(Message.SomeError)
                    publish(Label.SomeError)
                    return@launch
                }
                val id = UserId("timur")
                dispatch(Message.SignedIn(id))
            }
        }

        private fun signUp(credentials: UserCredentials) {
            dispatch(Message.Loading)
            scope.launch {
                delay(2.seconds)
                dispatch(Message.SomeError)
                publish(Label.SomeError)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                Message.Loading -> copy(loading = true)
                Message.LoggedOut -> copy(currentUser = null, loading = false)
                is Message.SignedIn -> copy(currentUser = msg.id, loading = false)
                Message.SomeError -> copy(currentUser = null, loading = false)
            }
    }
}
