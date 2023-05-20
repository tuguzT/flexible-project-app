package io.github.tuguzt.flexibleproject.data.repository.user

import io.github.tuguzt.flexibleproject.data.DatabaseClient
import io.github.tuguzt.flexibleproject.data.model.UserEntity
import io.github.tuguzt.flexibleproject.data.model.UserEntity_
import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository
import io.objectbox.kotlin.query
import io.objectbox.query.QueryBuilder

class LocalUserRepository(private val client: DatabaseClient) : UserRepository {
    override suspend fun findById(id: UserId): User? {
        val query = client.userBox.query {
            val stringOrder = QueryBuilder.StringOrder.CASE_SENSITIVE
            equal(UserEntity_.uid, id.toString(), stringOrder)
        }
        val entity = query.findFirst()
        return entity?.toDomain()
    }
}

private fun UserEntity.toDomain(): User = User(
    id = UserId(requireNotNull(uid)),
    data = UserData(
        name = requireNotNull(name),
        displayName = requireNotNull(displayName),
        role = Role.valueOf(requireNotNull(role)),
        email = email,
        avatarUrl = avatarUrl,
    ),
)
