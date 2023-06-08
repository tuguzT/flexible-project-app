package io.github.tuguzt.flexibleproject.domain.usecase.user

import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.error
import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.success
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserFilters
import io.github.tuguzt.flexibleproject.domain.model.user.UserIdFilters
import io.github.tuguzt.flexibleproject.domain.repository.user.CurrentUserRepository
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.firstOrNull

class DeleteCurrentUser(
    private val userRepository: UserRepository,
    private val currentUserRepository: CurrentUserRepository,
) {
    suspend fun delete(): Result<User, Exception> {
        val currentUser = currentUserRepository.read().firstOrNull()
            ?: return error(Exception.NoCurrentUser)
        val id = currentUser.id

        val filters = UserFilters(id = UserIdFilters(eq = Equal(id)))
        val user = when (val result = userRepository.read(filters)) {
            is Result.Error -> return error(Exception.Repository(result.error))
            is Result.Success -> result.data.firstOrNull()?.firstOrNull()
        }
        user ?: return error(Exception.NoCurrentUser)

        val deleted = when (val result = userRepository.delete(id)) {
            is Result.Error -> return error(Exception.Repository(result.error))
            is Result.Success -> result.data
        }
        return when (val result = currentUserRepository.set(null)) {
            is Result.Error -> error(Exception.Repository(result.error))
            is Result.Success -> success(deleted)
        }
    }

    sealed class Exception(message: String?, cause: Throwable?) : kotlin.Exception(message, cause) {
        object NoCurrentUser : Exception(
            message = "Current user was not present",
            cause = null,
        )

        data class Repository(val error: BaseException) : Exception(error.message, error.cause)
    }
}
