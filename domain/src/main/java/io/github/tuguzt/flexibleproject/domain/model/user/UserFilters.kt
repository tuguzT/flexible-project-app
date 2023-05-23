package io.github.tuguzt.flexibleproject.domain.model.user

import io.github.tuguzt.flexibleproject.domain.model.filter.Filter
import io.github.tuguzt.flexibleproject.domain.model.filter.satisfies

data class UserFilters(
    val id: UserIdFilters? = null,
    val data: UserDataFilters? = null,
) : Filter<User> {
    override fun satisfies(input: User): Boolean =
        id satisfies input.id && data satisfies input.data
}
