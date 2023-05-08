package io.github.tuguzt.flexibleproject.domain.usecase.user

import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository

class FindUserById(private val repository: UserRepository) {
    suspend fun findById(id: UserId): User? = repository.findById(id)
}