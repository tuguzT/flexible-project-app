package io.github.tuguzt.flexibleproject.viewmodel.auth.store

import com.arkivanov.mvikotlin.core.store.Store
import io.github.tuguzt.flexibleproject.domain.model.user.UserCredentials
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStore.State

interface AuthStore : Store<Intent, State, Label> {
    sealed interface Intent {
        data class SignIn(val credentials: UserCredentials) : Intent
        data class SignUp(val credentials: UserCredentials) : Intent
        object LogOut : Intent
    }

    data class State(
        val currentUser: UserId?,
        val isLoading: Boolean,
    )

    sealed interface Label {
        object SomeError : Label
    }
}
