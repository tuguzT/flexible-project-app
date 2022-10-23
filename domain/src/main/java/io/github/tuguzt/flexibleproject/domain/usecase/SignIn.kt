package io.github.tuguzt.flexibleproject.domain.usecase

import io.github.tuguzt.flexibleproject.domain.Result
import io.github.tuguzt.flexibleproject.domain.model.UserCredentials
import io.github.tuguzt.flexibleproject.domain.model.UserToken

public interface SignIn {
    public suspend fun signIn(credentials: UserCredentials): Result<UserToken, Error>

    public class Error(override val message: String? = null) : Exception(message)
}
