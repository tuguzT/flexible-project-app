package io.github.tuguzt.flexibleproject.domain.usecase.user

import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.error
import io.github.tuguzt.flexibleproject.domain.model.success
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.repository.user.CurrentUserRepository
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.firstOrNull

class SignOut(
    private val userRepository: UserRepository,
    private val currentUserRepository: CurrentUserRepository,
) {
    suspend fun signOut(): Result<User, Exception> {
        val currentUser = currentUserRepository.read().firstOrNull()
            ?: return error(Exception.NotSignedIn)

        val signedOut = when (val result = userRepository.signOut(currentUser.id)) {
            is Result.Error -> return error(Exception.Repository(result.error))
            is Result.Success -> result.data
        }
        return when (val result = currentUserRepository.set(null)) {
            is Result.Error -> error(Exception.Repository(result.error))
            is Result.Success -> success(signedOut)
        }
    }

    sealed class Exception(message: String?, cause: Throwable?) : kotlin.Exception(message, cause) {
        object NotSignedIn : Exception(
            message = "User was not signed in the system",
            cause = null,
        )

        data class Repository(val error: BaseException) : Exception(error.message, error.cause)
    }
}
