package io.github.tuguzt.flexibleproject.domain.usecase

import io.github.tuguzt.flexibleproject.domain.Result
import io.github.tuguzt.flexibleproject.domain.model.UserCredentials

public interface UserCredentialsVerifier {
    public fun verify(credentials: UserCredentials): Result<UserCredentialsState, Error>

    public class Error(override val message: String? = null) : Exception(message)
}
