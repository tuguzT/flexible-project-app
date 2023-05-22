package io.github.tuguzt.flexibleproject.domain.model.filter

data class NotBetween<T>(
    val min: T,
    val max: T,
) : Filter<T> where T : Comparable<T> {
    override fun satisfies(input: T): Boolean = input < min || max < input
}
