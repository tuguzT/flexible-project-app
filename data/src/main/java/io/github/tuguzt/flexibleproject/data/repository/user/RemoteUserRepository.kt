package io.github.tuguzt.flexibleproject.data.repository.user

import com.apollographql.apollo3.api.Optional
import io.github.tuguzt.flexibleproject.data.FilterUsersQuery
import io.github.tuguzt.flexibleproject.data.RemoteClient
import io.github.tuguzt.flexibleproject.data.type.UserFilters
import io.github.tuguzt.flexibleproject.data.type.UserRole
import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository

class RemoteUserRepository(private val client: RemoteClient) : UserRepository {
    override suspend fun findById(id: UserId): User? {
        val filters = UserFilters(id = Optional.present(id.toString()))
        val query = FilterUsersQuery(filters)
        val response = client.client.query(query).execute()
        val user = response.data?.users?.firstOrNull()
        return user?.toDomain()
    }
}

private fun FilterUsersQuery.User.toDomain(): User = User(
    id = UserId(id),
    data = UserData(
        name = name,
        displayName = displayName,
        role = role.toDomain(),
        email = email,
        avatarUrl = avatarUrl,
    ),
)

private fun UserRole.toDomain(): Role = Role.valueOf(
    rawValue.lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    },
)
