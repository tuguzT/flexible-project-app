package io.github.tuguzt.flexibleproject.domain.repository.project

import io.github.tuguzt.flexibleproject.domain.model.project.Project
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectData
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectFilters
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectId
import io.github.tuguzt.flexibleproject.domain.model.project.UpdateProject
import io.github.tuguzt.flexibleproject.domain.repository.RepositoryResult
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    suspend fun create(data: ProjectData): RepositoryResult<Project>

    suspend fun read(filters: ProjectFilters): RepositoryResult<Flow<List<Project>>>

    suspend fun update(id: ProjectId, update: UpdateProject): RepositoryResult<Project>

    suspend fun delete(id: ProjectId): RepositoryResult<Project>
}
