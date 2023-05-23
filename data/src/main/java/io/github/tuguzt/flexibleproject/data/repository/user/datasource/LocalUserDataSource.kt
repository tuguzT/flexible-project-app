package io.github.tuguzt.flexibleproject.data.repository.user.datasource

import io.github.tuguzt.flexibleproject.data.LocalClient
import io.github.tuguzt.flexibleproject.data.repository.user.model.UserEntity
import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.domain.model.user.UserId

internal class LocalUserDataSource(private val client: LocalClient)

private fun UserEntity.toDomain(): User = User(
    id = UserId(requireNotNull(uid)),
    data = UserData(
        name = requireNotNull(name),
        displayName = requireNotNull(displayName),
        role = Role.valueOf(requireNotNull(role)),
        email = email,
        avatar = avatar,
    ),
)
