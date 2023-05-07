package io.github.tuguzt.flexibleproject.domain.model.workspace

import io.github.tuguzt.flexibleproject.domain.model.Node

data class Workspace(
    override val id: WorkspaceId,
    val data: WorkspaceData,
) : Node {
    override fun equals(other: Any?) = other is Workspace && Essential(this) == Essential(other)
    override fun hashCode() = Essential(this).hashCode()

    private data class Essential(val id: WorkspaceId) {
        constructor(workspace: Workspace) : this(id = workspace.id)
    }
}
