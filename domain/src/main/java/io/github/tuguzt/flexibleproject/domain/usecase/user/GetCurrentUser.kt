package io.github.tuguzt.flexibleproject.domain.usecase.user

import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.repository.user.CurrentUserRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentUser(private val repository: CurrentUserRepository) {
    fun currentUser(): Flow<User?> = repository.read()
}
