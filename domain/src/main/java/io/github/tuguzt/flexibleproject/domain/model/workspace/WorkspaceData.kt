package io.github.tuguzt.flexibleproject.domain.model.workspace

data class WorkspaceData(
    val name: Name,
    val description: Description,
    val visibility: Visibility,
    val image: Image?,
)
