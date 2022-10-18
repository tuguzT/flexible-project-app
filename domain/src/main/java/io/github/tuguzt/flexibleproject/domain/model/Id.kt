package io.github.tuguzt.flexibleproject.domain.model

@JvmInline
public value class Id<@Suppress("unused") Owner : Node>(public val id: String)

public fun <Owner : Node, Other : Node> Id<Owner>.changeOwner(): Id<Other> = Id(this.id)
