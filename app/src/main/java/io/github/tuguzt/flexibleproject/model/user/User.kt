package io.github.tuguzt.flexibleproject.model.user

data class User(
    val name: String,
    val displayName: String,
    val role: Role,
    val email: String?,
)
