package io.github.tuguzt.flexibleproject.domain.usecase.user

import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserCredentials
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository

class SignIn(private val repository: UserRepository) {
    suspend fun signIn(credentials: UserCredentials): User = repository.signIn(credentials)
}
