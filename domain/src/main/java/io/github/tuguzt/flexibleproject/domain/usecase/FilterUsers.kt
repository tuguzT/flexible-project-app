package io.github.tuguzt.flexibleproject.domain.usecase

import io.github.tuguzt.flexibleproject.domain.model.User
import io.github.tuguzt.flexibleproject.domain.model.UserFilters

public interface FilterUsers {
    public suspend fun filter(filters: UserFilters): List<User>
}
