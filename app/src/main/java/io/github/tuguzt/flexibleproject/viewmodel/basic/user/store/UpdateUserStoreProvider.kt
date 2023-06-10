package io.github.tuguzt.flexibleproject.viewmodel.basic.user.store

import android.util.Patterns
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.user.Avatar
import io.github.tuguzt.flexibleproject.domain.model.user.DisplayName
import io.github.tuguzt.flexibleproject.domain.model.user.Email
import io.github.tuguzt.flexibleproject.domain.model.user.Name
import io.github.tuguzt.flexibleproject.domain.model.user.UpdateUser
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.domain.usecase.user.GetCurrentUser
import io.github.tuguzt.flexibleproject.domain.usecase.user.UpdateCurrentUser
import io.github.tuguzt.flexibleproject.domain.usecase.user.UpdateCurrentUser.Exception
import io.github.tuguzt.flexibleproject.viewmodel.StoreProvider
import io.github.tuguzt.flexibleproject.viewmodel.basic.user.store.UpdateUserStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.user.store.UpdateUserStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.user.store.UpdateUserStore.State
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UpdateUserStoreProvider(
    private val currentUser: GetCurrentUser,
    private val updateUser: UpdateCurrentUser,
    private val storeFactory: StoreFactory,
    private val coroutineContext: CoroutineContext,
) : StoreProvider<Intent, State, Label> {
    override fun provide(): UpdateUserStore =
        object :
            UpdateUserStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = UpdateUserStore::class.simpleName,
                initialState = State(
                    name = "",
                    displayName = "",
                    email = null,
                    avatar = null,
                    valid = false,
                    loading = false,
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Message {
        object Loading : Message
        data class NameChanged(val name: Name, val valid: Boolean) : Message
        data class DisplayNameChanged(val displayName: DisplayName, val valid: Boolean) : Message
        data class EmailChanged(val email: Email?, val valid: Boolean) : Message
        data class AvatarChanged(val avatar: Avatar?, val valid: Boolean) : Message
        object UserUpdated : Message
        object Error : Message
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Message, Label>(
        mainContext = coroutineContext,
    ) {
        private var currentUserData: UserData? = null

        override fun executeAction(action: Unit, getState: () -> State) {
            dispatch(Message.Loading)
            scope.launch {
                val data = currentUser.currentUser().firstOrNull()?.data ?: TODO()
                currentUserData = data
                dispatch(Message.UserUpdated)
                dispatch(Message.NameChanged(name = data.name, valid = false))
                dispatch(Message.DisplayNameChanged(displayName = data.displayName, valid = false))
                dispatch(Message.EmailChanged(email = data.email, valid = false))
                dispatch(Message.AvatarChanged(avatar = data.avatar, valid = false))
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.ChangeAvatar -> changeAvatar(intent.avatar, getState())
                is Intent.ChangeDisplayName -> changeDisplayName(intent.displayName, getState())
                is Intent.ChangeEmail -> changeEmail(intent.email, getState())
                is Intent.ChangeName -> changeName(intent.name, getState())
                Intent.UpdateUser -> updateUser(getState())
            }

        private fun changeName(name: Name, state: State) {
            val valid = run {
                val user = currentUserData ?: return@run false
                checkIfValid(name, state.displayName, state.email, state.avatar) &&
                    name.trim() != user.name
            }
            dispatch(Message.NameChanged(name, valid))
        }

        private fun changeDisplayName(displayName: DisplayName, state: State) {
            val valid = run {
                val user = currentUserData ?: return@run false
                checkIfValid(state.name, displayName, state.email, state.avatar) &&
                    displayName.trim() != user.displayName
            }
            dispatch(Message.DisplayNameChanged(displayName, valid))
        }

        private fun changeEmail(email: Email?, state: State) {
            val valid = run {
                val user = currentUserData ?: return@run false
                checkIfValid(state.name, state.displayName, email, state.avatar) &&
                    email?.trim() != user.email
            }
            dispatch(Message.EmailChanged(email, valid))
        }

        private fun changeAvatar(avatar: Avatar?, state: State) {
            val valid = run {
                val user = currentUserData ?: return@run false
                checkIfValid(state.name, state.displayName, state.email, avatar) &&
                    avatar?.trim() != user.avatar
            }
            dispatch(Message.AvatarChanged(avatar, valid))
        }

        private fun checkIfValid(
            name: Name,
            displayName: DisplayName,
            email: Email?,
            avatar: Avatar?,
        ): Boolean =
            name.isNotBlank() &&
                displayName.isNotBlank() &&
                email?.trim()?.let { Patterns.EMAIL_ADDRESS.matcher(it).matches() } ?: true &&
                avatar?.trim()?.let { Patterns.WEB_URL.matcher(it).matches() } ?: true

        private fun updateUser(state: State) {
            val user = currentUserData
            val update = UpdateUser(
                name = state.name.takeIf { it != user?.name },
                displayName = state.displayName.takeIf { it != user?.displayName },
                email = state.email.takeIf { it != user?.email },
                avatar = state.avatar.takeIf { it != user?.avatar },
            )
            dispatch(Message.Loading)
            scope.launch {
                when (val result = updateUser.update(update)) {
                    is Result.Success -> {
                        dispatch(Message.UserUpdated)
                        publish(Label.UserUpdated)
                    }

                    is Result.Error -> {
                        dispatch(Message.Error)
                        when (val error = result.error) {
                            Exception.NoCurrentUser -> publish(Label.NoCurrentUser)
                            is Exception.EmailAlreadyTaken -> publish(Label.EmailAlreadyTaken(error.email))
                            is Exception.NameAlreadyTaken -> publish(Label.NameAlreadyTaken(error.name))
                            is Exception.Repository -> when (error.error) {
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
                Message.UserUpdated -> copy(loading = false)
                Message.Error -> copy(loading = false)
                is Message.EmailChanged -> copy(email = msg.email, valid = msg.valid)
                is Message.AvatarChanged -> copy(avatar = msg.avatar, valid = msg.valid)
                is Message.NameChanged -> copy(name = msg.name, valid = msg.valid)
                is Message.DisplayNameChanged -> copy(
                    displayName = msg.displayName,
                    valid = msg.valid,
                )
            }
    }
}
