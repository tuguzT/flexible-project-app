package io.github.tuguzt.flexibleproject.domain.usecase.user

import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.error
import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.success
import io.github.tuguzt.flexibleproject.domain.model.user.Email
import io.github.tuguzt.flexibleproject.domain.model.user.EmailFilters
import io.github.tuguzt.flexibleproject.domain.model.user.Name
import io.github.tuguzt.flexibleproject.domain.model.user.NameFilters
import io.github.tuguzt.flexibleproject.domain.model.user.UpdateUser
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserDataFilters
import io.github.tuguzt.flexibleproject.domain.model.user.UserFilters
import io.github.tuguzt.flexibleproject.domain.model.user.UserIdFilters
import io.github.tuguzt.flexibleproject.domain.repository.user.CurrentUserRepository
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.firstOrNull

class UpdateCurrentUser(
    private val userRepository: UserRepository,
    private val currentUserRepository: CurrentUserRepository,
) {
    suspend fun update(update: UpdateUser): Result<User, Exception> {
        val currentUser = currentUserRepository.currentUserFlow.value
            ?: return error(Exception.NoCurrentUser)
        val id = currentUser.id

        val filters = UserFilters(id = UserIdFilters(eq = Equal(id)))
        val user = when (val result = userRepository.read(filters)) {
            is Result.Error -> return error(Exception.Repository(result.error))
            is Result.Success -> result.data.firstOrNull()?.firstOrNull()
        }
        user ?: return error(Exception.NoCurrentUser)

        @Suppress("NAME_SHADOWING")
        if (update.name != null) {
            val name = update.name
            val dataFilters = UserDataFilters(name = NameFilters(eq = Equal(name)))
            val filters = UserFilters(data = dataFilters)
            val user = when (val result = userRepository.read(filters)) {
                is Result.Error -> return error(Exception.Repository(result.error))
                is Result.Success -> result.data.firstOrNull()?.firstOrNull()
            }
            if (user != null) {
                return error(Exception.NameAlreadyTaken(name))
            }
        }

        @Suppress("NAME_SHADOWING")
        if (update.email != null) {
            val email = update.email
            val dataFilters = UserDataFilters(email = EmailFilters(eq = Equal(email)))
            val filters = UserFilters(data = dataFilters)
            val user = when (val result = userRepository.read(filters)) {
                is Result.Error -> return error(Exception.Repository(result.error))
                is Result.Success -> result.data.firstOrNull()
            }
            if (user != null) {
                return error(Exception.EmailAlreadyTaken(email))
            }
        }

        return when (val result = userRepository.update(id, update)) {
            is Result.Error -> error(Exception.Repository(result.error))
            is Result.Success -> {
                @Suppress("NAME_SHADOWING")
                val user = result.data
                currentUserRepository.setCurrentUser(user)
                success(user)
            }
        }
    }

    sealed class Exception(message: String?, cause: Throwable?) : kotlin.Exception(message, cause) {
        object NoCurrentUser : Exception(
            message = "Current user was not present",
            cause = null,
        )

        data class NameAlreadyTaken(val name: Name) : Exception(
            message = """User with name "$name" already exists """,
            cause = null,
        )

        data class EmailAlreadyTaken(val email: Email) : Exception(
            message = """User with email "$email" already exists """,
            cause = null,
        )

        data class Repository(val error: BaseException) : Exception(error.message, error.cause)
    }
}
