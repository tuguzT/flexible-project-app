package io.github.tuguzt.flexibleproject.domain.model.user

import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.filter.Filter
import io.github.tuguzt.flexibleproject.domain.model.filter.In
import io.github.tuguzt.flexibleproject.domain.model.filter.NotEqual
import io.github.tuguzt.flexibleproject.domain.model.filter.NotIn
import io.github.tuguzt.flexibleproject.domain.model.filter.Regex
import io.github.tuguzt.flexibleproject.domain.model.filter.satisfies

data class AvatarFilters(
    val eq: Equal<Avatar?>? = null,
    val ne: NotEqual<Avatar?>? = null,
    val `in`: In<Avatar?>? = null,
    val nin: NotIn<Avatar?>? = null,
    val regex: Regex?,
) : Filter<Avatar?> {
    override fun satisfies(input: Avatar?): Boolean =
        eq satisfies input &&
            ne satisfies input &&
            `in` satisfies input &&
            nin satisfies input &&
            input?.let { regex satisfies input } ?: true
}
