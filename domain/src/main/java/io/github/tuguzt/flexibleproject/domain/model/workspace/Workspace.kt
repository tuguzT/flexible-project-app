package io.github.tuguzt.flexibleproject.domain.model.workspace

import io.github.tuguzt.flexibleproject.domain.model.Node

data class Workspace(
    override val id: WorkspaceId,
    val data: WorkspaceData,
) : Node
