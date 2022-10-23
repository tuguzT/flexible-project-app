package io.github.tuguzt.flexibleproject.data.interactor

import io.github.tuguzt.flexibleproject.domain.Result
import io.github.tuguzt.flexibleproject.domain.error
import io.github.tuguzt.flexibleproject.domain.model.UserCredentials
import io.github.tuguzt.flexibleproject.domain.success
import io.github.tuguzt.flexibleproject.domain.usecase.UsernameVerifier
import io.github.tuguzt.flexibleproject.domain.usecase.PasswordVerifier
import io.github.tuguzt.flexibleproject.domain.usecase.UserCredentialsState
import io.github.tuguzt.flexibleproject.domain.usecase.UserCredentialsVerifier.Error
import io.github.tuguzt.flexibleproject.domain.usecase.UserCredentialsVerifier as DomainUserCredentialsVerifier

class UserCredentialsVerifier(
    private val usernameVerifier: UsernameVerifier,
    private val passwordVerifier: PasswordVerifier,
) : DomainUserCredentialsVerifier {

    override fun verify(credentials: UserCredentials): Result<UserCredentialsState, Error> {
        when (val result = usernameVerifier.verify(credentials.name)) {
            is Result.Error -> return error(error = Error(result.error.message))
            is Result.Success -> if (!result.data) {
                return success(data = UserCredentialsState.InvalidUsername)
            }
        }
        when (val result = passwordVerifier.verify(credentials.password)) {
            is Result.Error -> return error(error = Error(result.error.message))
            is Result.Success -> if (!result.data) {
                return success(data = UserCredentialsState.InvalidPassword)
            }
        }
        return success(data = UserCredentialsState.Valid)
    }
}
