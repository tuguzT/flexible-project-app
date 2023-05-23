package io.github.tuguzt.flexibleproject.domain.model

import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.filter.Filter
import io.github.tuguzt.flexibleproject.domain.model.filter.In
import io.github.tuguzt.flexibleproject.domain.model.filter.NotEqual
import io.github.tuguzt.flexibleproject.domain.model.filter.NotIn
import io.github.tuguzt.flexibleproject.domain.model.filter.satisfies

data class IdFilters<Owner : Node>(
    val eq: Equal<Id<Owner>>? = null,
    val ne: NotEqual<Id<Owner>>? = null,
    val `in`: In<Id<Owner>>? = null,
    val nin: NotIn<Id<Owner>>? = null,
) : Filter<Id<Owner>> {
    override fun satisfies(input: Id<Owner>): Boolean =
        eq satisfies input &&
            ne satisfies input &&
            `in` satisfies input &&
            nin satisfies input

    fun erase(): IdFilters<*> = this

    fun <Other : Node> withOwner(): IdFilters<Other> = IdFilters(
        eq = eq?.value?.let { id -> Equal(id.withOwner()) },
        ne = ne?.value?.let { id -> NotEqual(id.withOwner()) },
        `in` = `in`?.iterable?.let { ids -> In(ids.map { id -> id.withOwner() }) },
        nin = nin?.iterable?.let { ids -> NotIn(ids.map { id -> id.withOwner() }) },
    )
}
