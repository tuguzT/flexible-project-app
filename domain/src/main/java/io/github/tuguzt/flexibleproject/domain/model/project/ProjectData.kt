package io.github.tuguzt.flexibleproject.domain.model.project

import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId

data class ProjectData(
    val workspace: WorkspaceId,
    val name: Name,
    val description: Description,
    val visibility: Visibility,
    val image: Image,
    // TODO methodology, current stage
)
