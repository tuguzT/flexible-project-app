package io.github.tuguzt.flexibleproject.domain.usecase.user

import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.error
import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.success
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserFilters
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.domain.model.user.UserIdFilters
import io.github.tuguzt.flexibleproject.domain.repository.user.CurrentUserRepository
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository

class SignOut(
    private val userRepository: UserRepository,
    private val currentUserRepository: CurrentUserRepository,
) {
    suspend fun signOut(id: UserId): Result<User, Exception> {
        currentUserRepository.currentUserFlow.value
            ?: return error(Exception.NotSignedIn)

        val filters = UserFilters(id = UserIdFilters(eq = Equal(id)))
        val user = when (val result = userRepository.read(filters)) {
            is Result.Error -> return error(Exception.Repository(result.error))
            is Result.Success -> result.data.firstOrNull()
        }
        user ?: return error(Exception.NoUser(id))

        return when (val result = userRepository.signOut(id)) {
            is Result.Error -> error(Exception.Repository(result.error))
            is Result.Success -> {
                currentUserRepository.setCurrentUser(null)
                success(result.data)
            }
        }
    }

    sealed class Exception(message: String?, cause: Throwable?) : kotlin.Exception(message, cause) {
        data class NoUser(val id: UserId) : Exception(
            message = """No user was found by identifier "$id"""",
            cause = null,
        )

        object NotSignedIn : Exception(
            message = "User was not signed in the system",
            cause = null,
        )

        data class Repository(val error: BaseException) : Exception(error.message, error.cause)
    }
}
