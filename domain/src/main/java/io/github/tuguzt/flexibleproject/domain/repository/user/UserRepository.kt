package io.github.tuguzt.flexibleproject.domain.repository.user

import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserFilters

interface UserRepository {
    suspend fun read(filters: UserFilters): List<User>
}
