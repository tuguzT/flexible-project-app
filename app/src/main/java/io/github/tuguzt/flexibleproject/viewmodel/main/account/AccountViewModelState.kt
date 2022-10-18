package io.github.tuguzt.flexibleproject.viewmodel.main.account

import io.github.tuguzt.flexibleproject.domain.model.User
import io.github.tuguzt.flexibleproject.viewmodel.Message
import io.github.tuguzt.flexibleproject.viewmodel.MessageState

data class AccountViewModelState(
    val currentUser: User? = null,
    val isLoading: Boolean = true,
    override val messages: List<Message<AccountMessageKind>> = listOf(),
) : MessageState<AccountMessageKind>

inline val AccountViewModelState.isSignedIn: Boolean
    get() = currentUser != null
