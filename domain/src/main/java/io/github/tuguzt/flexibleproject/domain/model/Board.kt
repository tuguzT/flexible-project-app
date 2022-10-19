package io.github.tuguzt.flexibleproject.domain.model

public data class Board(
    override val id: Id<Board>,
    val name: String,
    val description: String,
    val tasks: List<Task>,
    val owner: User,
) : Node
