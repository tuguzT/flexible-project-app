package io.github.tuguzt.flexibleproject.domain.model.filter

data class And<in Input>(
    val first: Filter<Input>,
    val second: Filter<Input>,
) : Filter<Input> {
    override fun satisfies(input: Input): Boolean =
        (first satisfies input) && (second satisfies input)
}
