package io.github.tuguzt.flexibleproject.viewmodel.basic.user.store

import com.arkivanov.mvikotlin.core.store.Store
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.viewmodel.basic.user.store.CurrentUserStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.user.store.CurrentUserStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.user.store.CurrentUserStore.State

interface CurrentUserStore : Store<Intent, State, Label> {
    sealed interface Intent {
        object SignOut : Intent
        object Delete : Intent
    }

    data class State(
        val currentUser: User?,
        val loading: Boolean,
    )

    sealed interface Label {
        data class SignedInUp(val currentUser: User) : Label
        object SignedOut : Label
        object LocalStoreError : Label
        object NetworkAccessError : Label
        object UnknownError : Label
    }
}
