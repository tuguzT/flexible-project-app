package io.github.tuguzt.flexibleproject.domain.model.workspace

import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.filter.Filter
import io.github.tuguzt.flexibleproject.domain.model.filter.In
import io.github.tuguzt.flexibleproject.domain.model.filter.NotEqual
import io.github.tuguzt.flexibleproject.domain.model.filter.NotIn
import io.github.tuguzt.flexibleproject.domain.model.filter.satisfies

data class VisibilityFilters(
    val eq: Equal<Visibility>? = null,
    val ne: NotEqual<Visibility>? = null,
    val `in`: In<Visibility>? = null,
    val nin: NotIn<Visibility>? = null,
) : Filter<Visibility> {
    override fun satisfies(input: Visibility): Boolean =
        eq satisfies input &&
            ne satisfies input &&
            `in` satisfies input &&
            nin satisfies input
}
