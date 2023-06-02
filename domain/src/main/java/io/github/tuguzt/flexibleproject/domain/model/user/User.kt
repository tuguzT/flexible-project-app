package io.github.tuguzt.flexibleproject.domain.model.user

import io.github.tuguzt.flexibleproject.domain.model.Node

data class User(
    override val id: UserId,
    val data: UserData,
) : Node
