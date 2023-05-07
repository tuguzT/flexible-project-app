package io.github.tuguzt.flexibleproject.domain.model

@JvmInline
@Suppress("unused")
value class Id<Owner : Node>(private val value: String) {
    override fun toString(): String = value

    fun erase(): Id<*> = this

    fun <Other : Node> withOwner(): Id<Other> = Id(value)
}
