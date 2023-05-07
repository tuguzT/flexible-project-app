package io.github.tuguzt.flexibleproject.domain.model.workspace

data class WorkspaceData(
    val name: String,
    val description: String,
    val visibility: Visibility,
    val imageUrl: String?,
)
