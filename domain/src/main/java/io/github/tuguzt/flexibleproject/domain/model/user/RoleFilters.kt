package io.github.tuguzt.flexibleproject.domain.model.user

import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.filter.Filter
import io.github.tuguzt.flexibleproject.domain.model.filter.In
import io.github.tuguzt.flexibleproject.domain.model.filter.NotEqual
import io.github.tuguzt.flexibleproject.domain.model.filter.NotIn
import io.github.tuguzt.flexibleproject.domain.model.filter.satisfies

data class RoleFilters(
    val eq: Equal<Role>? = null,
    val ne: NotEqual<Role>? = null,
    val `in`: In<Role>? = null,
    val nin: NotIn<Role>? = null,
) : Filter<Role> {
    override fun satisfies(input: Role): Boolean =
        eq satisfies input &&
            ne satisfies input &&
            `in` satisfies input &&
            nin satisfies input
}
