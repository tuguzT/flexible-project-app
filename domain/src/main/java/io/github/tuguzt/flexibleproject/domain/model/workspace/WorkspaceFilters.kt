package io.github.tuguzt.flexibleproject.domain.model.workspace

import io.github.tuguzt.flexibleproject.domain.model.filter.Filter
import io.github.tuguzt.flexibleproject.domain.model.filter.satisfies

data class WorkspaceFilters(
    val id: WorkspaceIdFilters? = null,
    val data: WorkspaceDataFilters? = null,
) : Filter<Workspace> {
    override fun satisfies(input: Workspace): Boolean =
        id satisfies input.id && data satisfies input.data
}
