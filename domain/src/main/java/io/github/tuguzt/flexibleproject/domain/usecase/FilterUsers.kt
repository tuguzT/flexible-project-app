package io.github.tuguzt.flexibleproject.domain.usecase

import io.github.tuguzt.flexibleproject.domain.Result
import io.github.tuguzt.flexibleproject.domain.model.User
import io.github.tuguzt.flexibleproject.domain.model.UserFilters

public interface FilterUsers {
    public suspend fun filter(filters: UserFilters): Result<List<User>, Error>

    public class Error(override val message: String? = null) : Exception(message)
}
