package io.github.tuguzt.flexibleproject.domain.model.workspace

data class UpdateWorkspace(
    val name: Name? = null,
    val description: Description? = null,
    val visibility: Visibility? = null,
    val image: Image? = null,
)
