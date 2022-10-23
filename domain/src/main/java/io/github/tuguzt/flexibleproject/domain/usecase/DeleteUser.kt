package io.github.tuguzt.flexibleproject.domain.usecase

import io.github.tuguzt.flexibleproject.domain.Result
import io.github.tuguzt.flexibleproject.domain.model.Id
import io.github.tuguzt.flexibleproject.domain.model.User

public interface DeleteUser {
    public suspend fun delete(id: Id<User>): Result<User?, Error>

    public class Error(override val message: String? = null) : Exception(message)
}
