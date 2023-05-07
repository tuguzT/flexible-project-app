package io.github.tuguzt.flexibleproject.domain.model.user

data class UserData(
    val name: String,
    val displayName: String,
    val role: Role,
    val email: String?,
    val avatarUrl: String?,
)
