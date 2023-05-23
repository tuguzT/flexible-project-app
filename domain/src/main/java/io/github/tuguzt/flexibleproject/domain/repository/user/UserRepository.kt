package io.github.tuguzt.flexibleproject.domain.repository.user

import io.github.tuguzt.flexibleproject.domain.model.user.Name
import io.github.tuguzt.flexibleproject.domain.model.user.UpdateUser
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserFilters
import io.github.tuguzt.flexibleproject.domain.model.user.UserId

interface UserRepository {
    suspend fun create(name: Name): User

    suspend fun read(filters: UserFilters): List<User>

    suspend fun update(id: UserId, update: UpdateUser): User

    suspend fun delete(id: UserId): User
}
