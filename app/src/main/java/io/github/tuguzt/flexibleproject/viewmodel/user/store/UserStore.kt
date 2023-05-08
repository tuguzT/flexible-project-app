package io.github.tuguzt.flexibleproject.viewmodel.user.store

import com.arkivanov.mvikotlin.core.store.Store
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.State

interface UserStore : Store<Intent, State, Label> {
    sealed interface Intent {
        data class Load(val id: UserId) : Intent
    }

    data class State(
        val user: User?,
        val isLoading: Boolean,
    )

    sealed interface Label {
        data class NotFound(val id: UserId) : Label
    }
}
