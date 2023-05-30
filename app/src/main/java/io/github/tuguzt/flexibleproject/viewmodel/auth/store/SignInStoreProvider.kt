package io.github.tuguzt.flexibleproject.viewmodel.auth.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.user.UserCredentials
import io.github.tuguzt.flexibleproject.domain.usecase.user.SignIn
import io.github.tuguzt.flexibleproject.viewmodel.StoreProvider
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.SignInStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.SignInStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.SignInStore.State
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SignInStoreProvider(
    private val signIn: SignIn,
    private val storeFactory: StoreFactory,
    private val coroutineContext: CoroutineContext,
) : StoreProvider<Intent, State, Label> {
    override fun provide(): SignInStore =
        object :
            SignInStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = SignInStore::class.simpleName,
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
        data class PasswordChanged(val password: String, val valid: Boolean) : Message
        data class PasswordVisibleChanged(val passwordVisible: Boolean) : Message
        object Loading : Message
        object SignedIn : Message
        object Error : Message
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Message, Label>(
        mainContext = coroutineContext,
    ) {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.ChangeName -> changeName(intent.name, state = getState())
                is Intent.ChangePassword -> changePassword(intent.password, state = getState())
                is Intent.ChangePasswordVisible -> changePasswordVisible(intent.passwordVisible)
                is Intent.SignIn -> signIn(intent.credentials)
            }

        private fun changeName(name: String, state: State) {
            val valid = name.isNotBlank() && state.password.isNotBlank()
            dispatch(Message.NameChanged(name, valid))
        }

        private fun changePassword(password: String, state: State) {
            val valid = password.isNotBlank() && state.name.isNotBlank()
            dispatch(Message.PasswordChanged(password, valid))
        }

        private fun changePasswordVisible(passwordVisible: Boolean) {
            dispatch(Message.PasswordVisibleChanged(passwordVisible))
        }

        private fun signIn(credentials: UserCredentials) {
            dispatch(Message.Loading)
            scope.launch {
                when (val result = signIn.signIn(credentials)) {
                    is Result.Success -> dispatch(Message.SignedIn)
                    is Result.Error -> {
                        dispatch(Message.Error)
                        when (val error = result.error) {
                            is SignIn.Exception.AlreadySignedIn -> TODO()
                            is SignIn.Exception.NoUser -> {
                                publish(Label.NoUserWithName(credentials.name))
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
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                is Message.NameChanged -> copy(name = msg.name, valid = msg.valid)
                is Message.PasswordChanged -> copy(password = msg.password, valid = msg.valid)
                is Message.PasswordVisibleChanged -> copy(passwordVisible = msg.passwordVisible)
                Message.Loading -> copy(loading = true)
                Message.SignedIn -> copy(loading = false)
                Message.Error -> copy(loading = false)
            }
    }
}
