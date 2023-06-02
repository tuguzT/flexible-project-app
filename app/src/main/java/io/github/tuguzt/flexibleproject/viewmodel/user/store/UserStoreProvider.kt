package io.github.tuguzt.flexibleproject.viewmodel.user.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserFilters
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.domain.model.user.UserIdFilters
import io.github.tuguzt.flexibleproject.domain.usecase.user.FilterUsers
import io.github.tuguzt.flexibleproject.viewmodel.StoreProvider
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.State
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserStoreProvider(
    private val users: FilterUsers,
    private val storeFactory: StoreFactory,
    private val coroutineContext: CoroutineContext,
) : StoreProvider<Intent, State, Label> {
    override fun provide(): UserStore =
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
        object Error : Message
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Message, Label>(
        mainContext = coroutineContext,
    ) {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.Load -> load(intent.id)
            }

        private fun load(id: UserId) {
            dispatch(Message.Loading)
            scope.launch {
                val filters = UserFilters(id = UserIdFilters(eq = Equal(id)))
                val userFlow = when (val result = users.users(filters)) {
                    is Result.Success -> result.data
                    is Result.Error -> {
                        dispatch(Message.Error)
                        when (val error = result.error) {
                            is FilterUsers.Exception.Repository -> when (error.error) {
                                is BaseException.LocalStore -> publish(Label.LocalStoreError)
                                is BaseException.NetworkAccess -> publish(Label.NetworkAccessError)
                                is BaseException.Unknown -> publish(Label.UnknownError)
                            }
                        }
                        return@launch
                    }
                }
                userFlow.collect { users ->
                    val user = users.firstOrNull()
                    if (user == null) {
                        dispatch(Message.NotFound(id))
                        publish(Label.NotFound(id))
                        return@collect
                    }
                    dispatch(Message.Loaded(user))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                Message.Loading -> copy(loading = true)
                is Message.Loaded -> copy(user = msg.user, loading = false)
                is Message.NotFound -> copy(user = null, loading = false)
                Message.Error -> copy(loading = false)
            }
    }
}
