package io.github.tuguzt.flexibleproject.domain.model.filter

data class Equal<T>(val value: T) : Filter<T> {
    override fun satisfies(input: T): Boolean = value == input
}
