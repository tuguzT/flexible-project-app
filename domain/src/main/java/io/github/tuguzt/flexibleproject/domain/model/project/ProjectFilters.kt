package io.github.tuguzt.flexibleproject.domain.model.project

import io.github.tuguzt.flexibleproject.domain.model.filter.Filter
import io.github.tuguzt.flexibleproject.domain.model.filter.satisfies

data class ProjectFilters(
    val id: ProjectIdFilters? = null,
    val data: ProjectDataFilters? = null,
) : Filter<Project> {
    override fun satisfies(input: Project): Boolean =
        id satisfies input.id && data satisfies input.data
}
