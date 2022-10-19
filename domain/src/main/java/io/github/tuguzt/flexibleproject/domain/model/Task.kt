package io.github.tuguzt.flexibleproject.domain.model

import kotlinx.datetime.LocalDateTime

public data class Task(
    override val id: Id<Task>,
    val name: String,
    val description: String,
    val labels: List<Label>,
    val deadline: LocalDateTime,
    val isCompleted: Boolean,
) : Node
