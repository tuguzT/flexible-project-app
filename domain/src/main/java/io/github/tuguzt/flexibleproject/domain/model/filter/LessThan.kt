package io.github.tuguzt.flexibleproject.domain.model.filter

data class LessThan<T>(val value: T) : Filter<T> where T : Comparable<T> {
    override fun satisfies(input: T): Boolean = input < value
}
