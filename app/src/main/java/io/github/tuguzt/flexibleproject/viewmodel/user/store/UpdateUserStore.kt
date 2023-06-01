package io.github.tuguzt.flexibleproject.viewmodel.user.store

import com.arkivanov.mvikotlin.core.store.Store
import io.github.tuguzt.flexibleproject.domain.model.user.Avatar
import io.github.tuguzt.flexibleproject.domain.model.user.DisplayName
import io.github.tuguzt.flexibleproject.domain.model.user.Email
import io.github.tuguzt.flexibleproject.domain.model.user.Name
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UpdateUserStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UpdateUserStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UpdateUserStore.State

interface UpdateUserStore : Store<Intent, State, Label> {
    sealed interface Intent {
        data class ChangeName(val name: Name) : Intent
        data class ChangeDisplayName(val displayName: DisplayName) : Intent
        data class ChangeEmail(val email: Email?) : Intent
        data class ChangeAvatar(val avatar: Avatar?) : Intent
        object UpdateUser : Intent
    }

    data class State(
        val name: Name,
        val displayName: DisplayName,
        val email: Email?,
        val avatar: Avatar?,
        val valid: Boolean,
        val loading: Boolean,
    )

    sealed interface Label {
        object UserUpdated : Label
        object NoCurrentUser : Label
        data class NameAlreadyTaken(val name: Name) : Label
        data class EmailAlreadyTaken(val email: Email) : Label
        object LocalStoreError : Label
        object NetworkAccessError : Label
        object UnknownError : Label
    }
}
