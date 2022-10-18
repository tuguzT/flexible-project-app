package io.github.tuguzt.flexibleproject.viewmodel.auth

import io.github.tuguzt.flexibleproject.viewmodel.Message
import io.github.tuguzt.flexibleproject.viewmodel.MessageState

data class AuthViewModelState(
    val username: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    override val messages: List<Message<AuthMessageKind>> = listOf(),
) : MessageState<AuthMessageKind>
