package io.github.tuguzt.flexibleproject.domain.repository.user

import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserId

interface UserRepository {
    suspend fun findById(id: UserId): User?
}
