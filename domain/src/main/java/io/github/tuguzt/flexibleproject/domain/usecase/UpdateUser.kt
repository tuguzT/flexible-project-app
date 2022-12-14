package io.github.tuguzt.flexibleproject.domain.usecase

import io.github.tuguzt.flexibleproject.domain.model.User

public interface UpdateUser {
    public suspend fun update(user: User): User?

    public class Error(override val message: String? = null) : Exception(message)
}
