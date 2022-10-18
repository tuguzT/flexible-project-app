package io.github.tuguzt.flexibleproject.domain.usecase

import io.github.tuguzt.flexibleproject.domain.model.User
import io.github.tuguzt.flexibleproject.domain.model.UserCredentials
import io.github.tuguzt.flexibleproject.domain.model.UserToken

public interface SignIn {
    public suspend fun signIn(credentials: UserCredentials, token: UserToken): User
}
