package io.github.tuguzt.flexibleproject.domain.model.filter

data class NotIn<T>(val iterable: Iterable<T>) : Filter<T> {
    override fun satisfies(input: T): Boolean = input !in iterable
}
