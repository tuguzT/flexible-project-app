package io.github.tuguzt.flexibleproject.domain.usecase.user

import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserFilters
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository

class FilterUsers(private val repository: UserRepository) {
    suspend fun users(filters: UserFilters): List<User> = repository.read(filters)
}
