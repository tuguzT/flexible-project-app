package io.github.tuguzt.flexibleproject.viewmodel.auth.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserCredentials
import io.github.tuguzt.flexibleproject.domain.usecase.user.SignIn
import io.github.tuguzt.flexibleproject.domain.usecase.user.SignOut
import io.github.tuguzt.flexibleproject.domain.usecase.user.SignUp
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStore.State
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AuthStoreProvider(
    private val signIn: SignIn,
    private val signUp: SignUp,
    private val signOut: SignOut,
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
        data class SignedIn(val user: User) : Message
        data class SignedUp(val user: User) : Message
        data class SignedOut(val user: User) : Message
        object Error : Message
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Message, Label>(
        mainContext = coroutineContext,
    ) {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                Intent.SignOut -> signOut(state = getState())
                is Intent.SignIn -> signIn(intent.credentials)
                is Intent.SignUp -> signUp(intent.credentials)
            }

        private fun signOut(state: State) {
            val user = state.currentUser ?: return
            dispatch(Message.Loading)
            scope.launch {
                when (val result = signOut.signOut(user.id)) {
                    is Result.Success -> dispatch(Message.SignedOut(result.data))
                    is Result.Error -> {
                        dispatch(Message.Error)
                        when (val error = result.error) {
                            is SignOut.Exception.NoUser -> publish(Label.IdNotFound(user.id))
                            is SignOut.Exception.Repository -> when (error.error) {
                                is BaseException.LocalStore -> publish(Label.LocalStoreError)
                                is BaseException.NetworkAccess -> publish(Label.NetworkAccessError)
                                is BaseException.Unknown -> publish(Label.UnknownError)
                            }
                        }
                    }
                }
            }
        }

        private fun signIn(credentials: UserCredentials) {
            dispatch(Message.Loading)
            scope.launch {
                when (val result = signIn.signIn(credentials)) {
                    is Result.Success -> dispatch(Message.SignedIn(result.data))
                    is Result.Error -> {
                        dispatch(Message.Error)
                        when (val error = result.error) {
                            is SignIn.Exception.NoUser -> {
                                publish(Label.CredentialsNotFound(credentials))
                            }

                            is SignIn.Exception.Repository -> when (error.error) {
                                is BaseException.LocalStore -> publish(Label.LocalStoreError)
                                is BaseException.NetworkAccess -> publish(Label.NetworkAccessError)
                                is BaseException.Unknown -> publish(Label.UnknownError)
                            }
                        }
                    }
                }
            }
        }

        private fun signUp(credentials: UserCredentials) {
            dispatch(Message.Loading)
            scope.launch {
                when (val result = signUp.signUp(credentials)) {
                    is Result.Success -> dispatch(Message.SignedUp(result.data))
                    is Result.Error -> {
                        dispatch(Message.Error)
                        when (val error = result.error) {
                            is SignUp.Exception.NameAlreadyTaken -> {
                                publish(Label.NameAlreadyTaken(credentials.name))
                            }

                            is SignUp.Exception.Repository -> when (error.error) {
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
                is Message.SignedIn -> copy(currentUser = msg.user, loading = false)
                is Message.SignedUp -> copy(currentUser = msg.user, loading = false)
                is Message.SignedOut -> copy(currentUser = null, loading = false)
                Message.Error -> copy(loading = false)
            }
    }
}
