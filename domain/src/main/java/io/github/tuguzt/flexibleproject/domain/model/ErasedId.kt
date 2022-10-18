package io.github.tuguzt.flexibleproject.domain.model

@JvmInline
public value class ErasedId(public val id: String)

public fun <Owner : Node> ErasedId.withOwner(): Id<Owner> = Id(id)
