package io.github.tuguzt.flexibleproject.viewmodel.basic.user.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.usecase.user.DeleteCurrentUser
import io.github.tuguzt.flexibleproject.domain.usecase.user.GetCurrentUser
import io.github.tuguzt.flexibleproject.domain.usecase.user.SignOut
import io.github.tuguzt.flexibleproject.viewmodel.StoreProvider
import io.github.tuguzt.flexibleproject.viewmodel.basic.user.store.CurrentUserStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.user.store.CurrentUserStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.user.store.CurrentUserStore.State
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CurrentUserStoreProvider(
    private val currentUser: GetCurrentUser,
    private val signOut: SignOut,
    private val delete: DeleteCurrentUser,
    private val storeFactory: StoreFactory,
    private val coroutineContext: CoroutineContext,
) : StoreProvider<Intent, State, Label> {
    override fun provide(): CurrentUserStore =
        object :
            CurrentUserStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = CurrentUserStore::class.simpleName,
                initialState = State(
                    currentUser = null,
                    loading = false,
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Message {
        object Loading : Message
        data class UserUpdated(val currentUser: User) : Message
        object SignedOut : Message
        object Error : Message
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Message, Label>(
        mainContext = coroutineContext,
    ) {
        override fun executeAction(action: Unit, getState: () -> State) {
            scope.launch {
                currentUser.currentUser().filterNotNull().collect { currentUser ->
                    dispatch(Message.UserUpdated(currentUser))
                    publish(Label.SignedInUp(currentUser))
                }
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                Intent.SignOut -> signOut()
                Intent.Delete -> delete()
            }

        private fun delete() {
            dispatch(Message.Loading)
            scope.launch {
                when (val result = delete.delete()) {
                    is Result.Success -> {
                        dispatch(Message.SignedOut)
                        publish(Label.SignedOut)
                    }

                    is Result.Error -> {
                        dispatch(Message.Error)
                        when (val error = result.error) {
                            DeleteCurrentUser.Exception.NoCurrentUser -> TODO()
                            is DeleteCurrentUser.Exception.Repository -> when (error.error) {
                                is BaseException.LocalStore -> publish(Label.LocalStoreError)
                                is BaseException.NetworkAccess -> publish(Label.NetworkAccessError)
                                is BaseException.Unknown -> publish(Label.UnknownError)
                            }
                        }
                    }
                }
            }
        }

        private fun signOut() {
            dispatch(Message.Loading)
            scope.launch {
                when (val result = signOut.signOut()) {
                    is Result.Success -> {
                        dispatch(Message.SignedOut)
                        publish(Label.SignedOut)
                    }

                    is Result.Error -> {
                        dispatch(Message.Error)
                        when (val error = result.error) {
                            SignOut.Exception.NotSignedIn -> TODO()
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
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                Message.Loading -> copy(loading = true)
                is Message.UserUpdated -> copy(currentUser = msg.currentUser, loading = false)
                Message.SignedOut -> copy(currentUser = null, loading = false)
                Message.Error -> copy(loading = false)
            }
    }
}
