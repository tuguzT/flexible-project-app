package io.github.tuguzt.flexibleproject.data.repository.user.datasource

import io.github.tuguzt.flexibleproject.data.FilterUsersQuery
import io.github.tuguzt.flexibleproject.data.RemoteClient
import io.github.tuguzt.flexibleproject.data.type.UserRole
import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.domain.model.user.UserId

internal class RemoteUserDataSource(private val client: RemoteClient)

private fun FilterUsersQuery.User.toDomain(): User = User(
    id = UserId(id),
    data = UserData(
        name = name,
        displayName = displayName,
        role = role.toDomain(),
        email = email,
        avatar = avatarUrl,
    ),
)

private fun UserRole.toDomain(): Role = Role.valueOf(
    rawValue.lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    },
)
