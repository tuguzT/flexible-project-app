package io.github.tuguzt.flexibleproject.domain.model.filter

data class BetweenEqual<T>(
    val min: T,
    val max: T,
) : Filter<T> where T : Comparable<T> {
    constructor(range: ClosedRange<T>) : this(min = range.start, max = range.endInclusive)

    override fun satisfies(input: T): Boolean = input in min..max
}
