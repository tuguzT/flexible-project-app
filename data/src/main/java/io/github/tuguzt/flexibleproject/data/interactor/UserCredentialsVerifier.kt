package io.github.tuguzt.flexibleproject.data.interactor

import io.github.tuguzt.flexibleproject.domain.model.UserCredentials
import io.github.tuguzt.flexibleproject.domain.usecase.UsernameVerifier
import io.github.tuguzt.flexibleproject.domain.usecase.PasswordVerifier
import io.github.tuguzt.flexibleproject.domain.usecase.UserCredentialsVerifier as DomainUserCredentialsVerifier

class UserCredentialsVerifier(
    private val usernameVerifier: UsernameVerifier,
    private val passwordVerifier: PasswordVerifier,
) : DomainUserCredentialsVerifier {
    override fun verify(credentials: UserCredentials): Boolean {
        val usernameVerify = usernameVerifier.verify(credentials.name)
        val passwordVerify = passwordVerifier.verify(credentials.password)
        return usernameVerify and passwordVerify
    }
}
