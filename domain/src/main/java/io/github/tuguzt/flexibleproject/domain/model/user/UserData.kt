package io.github.tuguzt.flexibleproject.domain.model.user

data class UserData(
    val name: Name,
    val displayName: DisplayName,
    val role: Role,
    val email: Email?,
    val avatar: Avatar?,
)
