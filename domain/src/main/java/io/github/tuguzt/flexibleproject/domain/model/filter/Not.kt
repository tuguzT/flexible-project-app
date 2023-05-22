package io.github.tuguzt.flexibleproject.domain.model.filter

data class Not<in Input>(val filter: Filter<Input>) : Filter<Input> {
    override fun satisfies(input: Input): Boolean = (filter satisfies input).not()
}
