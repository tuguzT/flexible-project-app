package io.github.tuguzt.flexibleproject.domain.model.filter

fun interface Filter<in Input> {
    infix fun satisfies(input: Input): Boolean
}

operator fun <Input> Filter<Input>.not(): Filter<Input> = Not(this)

operator fun <Input> Filter<Input>.plus(other: Filter<Input>): Filter<Input> = And(this, other)
