package io.github.tuguzt.flexibleproject.domain.model.user

import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.filter.Filter
import io.github.tuguzt.flexibleproject.domain.model.filter.In
import io.github.tuguzt.flexibleproject.domain.model.filter.NotEqual
import io.github.tuguzt.flexibleproject.domain.model.filter.NotIn
import io.github.tuguzt.flexibleproject.domain.model.filter.Regex
import io.github.tuguzt.flexibleproject.domain.model.filter.satisfies

data class DisplayNameFilters(
    val eq: Equal<DisplayName>? = null,
    val ne: NotEqual<DisplayName>? = null,
    val `in`: In<DisplayName>? = null,
    val nin: NotIn<DisplayName>? = null,
    val regex: Regex? = null,
) : Filter<DisplayName> {
    override fun satisfies(input: DisplayName): Boolean =
        eq satisfies input &&
            ne satisfies input &&
            `in` satisfies input &&
            nin satisfies input &&
            regex satisfies input
}
