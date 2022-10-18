package io.github.tuguzt.flexibleproject.domain.usecase

import io.github.tuguzt.flexibleproject.domain.model.UserCredentials

public interface UserCredentialsVerifier {
    public fun verify(credentials: UserCredentials): Boolean
}
