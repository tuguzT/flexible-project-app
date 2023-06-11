package io.github.tuguzt.flexibleproject.domain.model.project

import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId

data class UpdateProject(
    val workspace: WorkspaceId? = null,
    val name: Name? = null,
    val description: Description? = null,
    val visibility: Visibility? = null,
    val image: Image? = null,
)
