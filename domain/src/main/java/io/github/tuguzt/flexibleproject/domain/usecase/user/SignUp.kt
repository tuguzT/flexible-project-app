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
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository

class SignUp(private val repository: UserRepository) {
    suspend fun signUp(credentials: UserCredentials): Result<User, Exception> {
        val name = credentials.name
        val dataFilters = UserDataFilters(name = NameFilters(eq = Equal(name)))
        val filters = UserFilters(data = dataFilters)
        val user = when (val result = repository.read(filters)) {
            is Result.Error -> return error(Exception.Repository(result.error))
            is Result.Success -> result.data.firstOrNull()
        }
        if (user != null) {
            return error(Exception.NameAlreadyTaken(name))
        }

        return when (val result = repository.signUp(credentials)) {
            is Result.Error -> error(Exception.Repository(result.error))
            is Result.Success -> success(result.data)
        }
    }

    sealed class Exception(message: String?, cause: Throwable?) : kotlin.Exception(message, cause) {
        data class NameAlreadyTaken(val name: Name) : Exception(
            message = """User with name "$name" already exists """,
            cause = null,
        )

        data class Repository(val error: BaseException) : Exception(error.message, error.cause)
    }
}
