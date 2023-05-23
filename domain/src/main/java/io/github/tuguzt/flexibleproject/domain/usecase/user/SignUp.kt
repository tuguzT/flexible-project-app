package io.github.tuguzt.flexibleproject.domain.usecase.user

import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserCredentials
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository

class SignUp(private val repository: UserRepository) {
    suspend fun signUp(credentials: UserCredentials): User = repository.signUp(credentials)
}
