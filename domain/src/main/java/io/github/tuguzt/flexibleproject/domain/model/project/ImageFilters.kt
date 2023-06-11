package io.github.tuguzt.flexibleproject.domain.model.project

import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.filter.Filter
import io.github.tuguzt.flexibleproject.domain.model.filter.In
import io.github.tuguzt.flexibleproject.domain.model.filter.NotEqual
import io.github.tuguzt.flexibleproject.domain.model.filter.NotIn
import io.github.tuguzt.flexibleproject.domain.model.filter.Regex
import io.github.tuguzt.flexibleproject.domain.model.filter.satisfies

data class ImageFilters(
    val eq: Equal<Image?>? = null,
    val ne: NotEqual<Image?>? = null,
    val `in`: In<Image?>? = null,
    val nin: NotIn<Image?>? = null,
    val regex: Regex?,
) : Filter<Image?> {
    override fun satisfies(input: Image?): Boolean =
        eq satisfies input &&
            ne satisfies input &&
            `in` satisfies input &&
            nin satisfies input &&
            input?.let { regex satisfies input } ?: true
}
