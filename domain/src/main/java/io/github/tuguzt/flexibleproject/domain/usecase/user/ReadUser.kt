package io.github.tuguzt.flexibleproject.domain.usecase.user

import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository

class ReadUser(private val repository: UserRepository) {
    suspend fun readUser(id: UserId): User? {
        return repository.read(id)
    }
}
