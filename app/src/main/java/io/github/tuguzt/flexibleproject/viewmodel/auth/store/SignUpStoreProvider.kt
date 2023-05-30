package io.github.tuguzt.flexibleproject.viewmodel.auth.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.user.UserCredentials
import io.github.tuguzt.flexibleproject.domain.usecase.user.SignUp
import io.github.tuguzt.flexibleproject.viewmodel.StoreProvider
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.SignUpStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.SignUpStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.SignUpStore.State
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SignUpStoreProvider(
    private val signUp: SignUp,
    private val storeFactory: StoreFactory,
    private val coroutineContext: CoroutineContext,
) : StoreProvider<Intent, State, Label> {
    override fun provide(): SignUpStore =
        object :
            SignUpStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = SignUpStore::class.simpleName,
                initialState = State(
                    name = "",
                    password = "",
                    repeatPassword = "",
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
        data class RepeatPasswordChanged(val repeatPassword: String, val valid: Boolean) : Message
        data class PasswordVisibleChanged(val passwordVisible: Boolean) : Message
        object Loading : Message
        object SignedUp : Message
        object Error : Message
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Message, Label>(
        mainContext = coroutineContext,
    ) {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.ChangeName -> changeName(intent.name, state = getState())
                is Intent.ChangePassword -> changePassword(intent.password, state = getState())
                is Intent.ChangeRepeatPassword -> changeRepeatPassword(
                    intent.repeatPassword,
                    state = getState(),
                )

                is Intent.ChangePasswordVisible -> changePasswordVisible(intent.passwordVisible)
                is Intent.SignUp -> signUp(intent.credentials)
            }

        private fun changeName(name: String, state: State) {
            val valid = name.isNotBlank() && state.password.isNotBlank() &&
                state.password.trim() == state.repeatPassword.trim()
            dispatch(Message.NameChanged(name, valid))
        }

        private fun changePassword(password: String, state: State) {
            val valid = password.isNotBlank() && state.name.isNotBlank() &&
                password.trim() == state.repeatPassword.trim()
            dispatch(Message.PasswordChanged(password, valid))
        }

        private fun changeRepeatPassword(repeatPassword: String, state: State) {
            val valid = repeatPassword.isNotBlank() && state.name.isNotBlank() &&
                state.password.trim() == repeatPassword.trim()
            dispatch(Message.RepeatPasswordChanged(repeatPassword, valid))
        }

        private fun changePasswordVisible(passwordVisible: Boolean) {
            dispatch(Message.PasswordVisibleChanged(passwordVisible))
        }

        private fun signUp(credentials: UserCredentials) {
            dispatch(Message.Loading)
            scope.launch {
                when (val result = signUp.signUp(credentials)) {
                    is Result.Success -> dispatch(Message.SignedUp)
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
                Message.Loading -> copy(loading = true)
                Message.SignedUp -> copy(loading = false)
                Message.Error -> copy(loading = false)
                is Message.NameChanged -> copy(name = msg.name, valid = msg.valid)
                is Message.PasswordChanged -> copy(password = msg.password, valid = msg.valid)
                is Message.PasswordVisibleChanged -> copy(passwordVisible = msg.passwordVisible)
                is Message.RepeatPasswordChanged -> copy(
                    repeatPassword = msg.repeatPassword,
                    valid = msg.valid,
                )
            }
    }
}
