package io.github.tuguzt.flexibleproject.domain.model.workspace

import io.github.tuguzt.flexibleproject.domain.model.filter.Filter
import io.github.tuguzt.flexibleproject.domain.model.filter.satisfies

data class WorkspaceDataFilters(
    val name: NameFilters? = null,
    val description: DescriptionFilters? = null,
    val visibility: VisibilityFilters? = null,
    val image: ImageFilters? = null,
) : Filter<WorkspaceData> {
    override fun satisfies(input: WorkspaceData): Boolean =
        name satisfies input.name &&
            description satisfies input.description &&
            visibility satisfies input.visibility &&
            image satisfies input.image
}
