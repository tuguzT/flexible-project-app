package io.github.tuguzt.flexibleproject.domain.model

public data class User(
    override val id: Id<User>,
    val name: String,
    val email: String?,
    val role: UserRole,
) : Node
