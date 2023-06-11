package io.github.tuguzt.flexibleproject.domain.model.project

import io.github.tuguzt.flexibleproject.domain.model.Node

data class Project(
    override val id: ProjectId,
    val data: ProjectData,
) : Node
