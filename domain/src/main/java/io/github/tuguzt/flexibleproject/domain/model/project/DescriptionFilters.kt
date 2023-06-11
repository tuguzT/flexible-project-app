package io.github.tuguzt.flexibleproject.domain.model.project

import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.filter.Filter
import io.github.tuguzt.flexibleproject.domain.model.filter.In
import io.github.tuguzt.flexibleproject.domain.model.filter.NotEqual
import io.github.tuguzt.flexibleproject.domain.model.filter.NotIn
import io.github.tuguzt.flexibleproject.domain.model.filter.Regex
import io.github.tuguzt.flexibleproject.domain.model.filter.satisfies

data class DescriptionFilters(
    val eq: Equal<Description>? = null,
    val ne: NotEqual<Description>? = null,
    val `in`: In<Description>? = null,
    val nin: NotIn<Description>? = null,
    val regex: Regex? = null,
) : Filter<Description> {
    override fun satisfies(input: Description): Boolean =
        eq satisfies input &&
            ne satisfies input &&
            `in` satisfies input &&
            nin satisfies input &&
            regex satisfies input
}
