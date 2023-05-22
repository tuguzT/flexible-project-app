package io.github.tuguzt.flexibleproject.domain.model.filter

data class Contains<T>(val value: T) : Filter<Iterable<T>> {
    override fun satisfies(input: Iterable<T>): Boolean = value in input
}
