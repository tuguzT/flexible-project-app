package io.github.tuguzt.flexibleproject.domain.model.filter

data class NotEqual<T>(val value: T) : Filter<T> {
    override fun satisfies(input: T): Boolean = value != input
}
