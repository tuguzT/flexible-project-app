package io.github.tuguzt.flexibleproject.domain.usecase.user

import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.error
import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.success
import io.github.tuguzt.flexibleproject.domain.model.user.Name
import io.github.tuguzt.flexibleproject.domain.model.user.NameFilters
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserCredentials
import io.github.tuguzt.flexibleproject.domain.model.user.UserDataFilters
import io.github.tuguzt.flexibleproject.domain.model.user.UserFilters
import io.github.tuguzt.flexibleproject.domain.repository.user.CurrentUserRepository
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.firstOrNull

class SignIn(
    private val userRepository: UserRepository,
    private val currentUserRepository: CurrentUserRepository,
) {
    suspend fun signIn(credentials: UserCredentials): Result<User, Exception> {
        val currentUser = currentUserRepository.currentUserFlow.value
        if (currentUser != null) {
            return error(Exception.AlreadySignedIn(currentUser))
        }

        val name = credentials.name
        val dataFilters = UserDataFilters(name = NameFilters(eq = Equal(name)))
        val filters = UserFilters(data = dataFilters)
        val user = when (val result = userRepository.read(filters)) {
            is Result.Error -> return error(Exception.Repository(result.error))
            is Result.Success -> result.data.firstOrNull()?.firstOrNull()
        }
        user ?: return error(Exception.NoUser(name))

        return when (val result = userRepository.signIn(credentials)) {
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
        data class NoUser(val name: Name) : Exception(
            message = """No user was found by provided name "$name"""",
            cause = null,
        )

        data class AlreadySignedIn(val user: User) : Exception(
            message = """User $user is already signed in the system""",
            cause = null,
        )

        data class Repository(val error: BaseException) : Exception(error.message, error.cause)
    }
}
