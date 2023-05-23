package io.github.tuguzt.flexibleproject.domain.model.user

import io.github.tuguzt.flexibleproject.domain.model.filter.Filter
import io.github.tuguzt.flexibleproject.domain.model.filter.satisfies

data class UserDataFilters(
    val name: NameFilters? = null,
    val displayName: DisplayNameFilters? = null,
    val role: RoleFilters? = null,
    val email: EmailFilters? = null,
    val avatar: AvatarFilters? = null,
) : Filter<UserData> {
    override fun satisfies(input: UserData): Boolean =
        name satisfies input.name &&
            displayName satisfies input.displayName &&
            role satisfies input.role &&
            email satisfies input.email &&
            avatar satisfies input.avatar
}
