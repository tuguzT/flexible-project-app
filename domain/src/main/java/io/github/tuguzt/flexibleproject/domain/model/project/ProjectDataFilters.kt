package io.github.tuguzt.flexibleproject.domain.model.project

import io.github.tuguzt.flexibleproject.domain.model.filter.Filter
import io.github.tuguzt.flexibleproject.domain.model.filter.satisfies
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceIdFilters

data class ProjectDataFilters(
    val workspace: WorkspaceIdFilters? = null,
    val name: NameFilters? = null,
    val description: DescriptionFilters? = null,
    val visibility: VisibilityFilters? = null,
    val image: ImageFilters? = null,
) : Filter<ProjectData> {
    override fun satisfies(input: ProjectData): Boolean =
        workspace satisfies input.workspace &&
            name satisfies input.name &&
            description satisfies input.description &&
            visibility satisfies input.visibility &&
            image satisfies input.image
}
