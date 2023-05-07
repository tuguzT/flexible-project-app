package io.github.tuguzt.flexibleproject.domain.model.user

import io.github.tuguzt.flexibleproject.domain.model.Node

data class User(
    override val id: UserId,
    val data: UserData,
) : Node {
    override fun equals(other: Any?) = other is User && Essential(this) == Essential(other)
    override fun hashCode() = Essential(this).hashCode()

    private data class Essential(val id: UserId) {
        constructor(user: User) : this(id = user.id)
    }
}
