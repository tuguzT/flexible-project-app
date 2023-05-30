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
import io.github.tuguzt.flexibleproject.domain.usecase.user.SignUp
import io.github.tuguzt.flexibleproject.viewmodel.StoreProvider
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStore.State
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AuthStoreProvider(
    private val signIn: SignIn,
    private val signUp: SignUp,
    private val storeFactory: StoreFactory,
    private val coroutineContext: CoroutineContext,
) : StoreProvider<Intent, State, Label> {
    override fun provide(): AuthStore =
        object :
            AuthStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = AuthStore::class.simpleName,
                initialState = State(
                    name = "",
                    password = "",
                    passwordVisible = false,
                    valid = false,
                    loading = false,
                ),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Message {
        data class NameChanged(val name: String, val valid: Boolean) : Message
        data class PasswordChanged(val password: String) : Message
        data class PasswordVisibleChanged(val passwordVisible: Boolean) : Message
        object Loading : Message
        data class SignedIn(val user: User) : Message
        data class SignedUp(val user: User) : Message
        object Error : Message
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Message, Label>(
        mainContext = coroutineContext,
    ) {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.ChangeName -> changeName(intent.name)
                is Intent.ChangePassword -> changePassword(intent.password)
                is Intent.ChangePasswordVisible -> changePasswordVisible(intent.passwordVisible)
                is Intent.SignIn -> signIn(intent.credentials)
                is Intent.SignUp -> signUp(intent.credentials)
            }

        private fun changeName(name: String) {
            val valid = name.isNotBlank()
            dispatch(Message.NameChanged(name, valid))
        }

        private fun changePassword(password: String) {
            dispatch(Message.PasswordChanged(password))
        }

        private fun changePasswordVisible(passwordVisible: Boolean) {
            dispatch(Message.PasswordVisibleChanged(passwordVisible))
        }

        private fun signIn(credentials: UserCredentials) {
            dispatch(Message.Loading)
            scope.launch {
                when (val result = signIn.signIn(credentials)) {
                    is Result.Success -> dispatch(Message.SignedIn(result.data))
                    is Result.Error -> {
                        dispatch(Message.Error)
                        when (val error = result.error) {
                            is SignIn.Exception.AlreadySignedIn -> TODO()
                            is SignIn.Exception.NoUser -> {
                                publish(Label.NotFoundByName(credentials.name))
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
                            is SignUp.Exception.AlreadySignedIn -> TODO()
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
                is Message.NameChanged -> copy(name = msg.name, valid = msg.valid)
                is Message.PasswordChanged -> copy(password = msg.password)
                is Message.PasswordVisibleChanged -> copy(passwordVisible = msg.passwordVisible)
                Message.Loading -> copy(loading = true)
                is Message.SignedIn -> copy(loading = false)
                is Message.SignedUp -> copy(loading = false)
                Message.Error -> copy(loading = false)
            }
    }
}
