package io.github.tuguzt.flexibleproject.domain.model.user

import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.filter.Filter
import io.github.tuguzt.flexibleproject.domain.model.filter.In
import io.github.tuguzt.flexibleproject.domain.model.filter.NotEqual
import io.github.tuguzt.flexibleproject.domain.model.filter.NotIn
import io.github.tuguzt.flexibleproject.domain.model.filter.Regex
import io.github.tuguzt.flexibleproject.domain.model.filter.satisfies

data class EmailFilters(
    val eq: Equal<Email?>? = null,
    val ne: NotEqual<Email?>? = null,
    val `in`: In<Email?>? = null,
    val nin: NotIn<Email?>? = null,
    val regex: Regex? = null,
) : Filter<Email?> {
    override fun satisfies(input: Email?): Boolean =
        eq satisfies input &&
            ne satisfies input &&
            `in` satisfies input &&
            nin satisfies input &&
            input?.let { regex satisfies input } ?: true
}
