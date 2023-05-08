package io.github.tuguzt.flexibleproject.viewmodel.user.store

import com.arkivanov.mvikotlin.core.store.Store
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.State

interface UserStore : Store<Intent, State, Label> {
    sealed interface Intent {
        object Load : Intent
    }

    data class State(
        val user: User?,
        val isLoading: Boolean,
    )

    sealed interface Label {
        object LoadError : Label
    }
}
