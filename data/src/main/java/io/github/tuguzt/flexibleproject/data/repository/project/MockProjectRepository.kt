package io.github.tuguzt.flexibleproject.data.repository.project

import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.error
import io.github.tuguzt.flexibleproject.domain.model.project.Project
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectData
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectFilters
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectId
import io.github.tuguzt.flexibleproject.domain.model.project.UpdateProject
import io.github.tuguzt.flexibleproject.domain.model.project.Visibility
import io.github.tuguzt.flexibleproject.domain.model.success
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.domain.repository.RepositoryResult
import io.github.tuguzt.flexibleproject.domain.repository.project.ProjectRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.UUID
import kotlin.time.Duration.Companion.seconds

class MockProjectRepository : ProjectRepository {
    override suspend fun create(data: ProjectData): RepositoryResult<Project> {
        delay(2.seconds)

        val id = ProjectId(UUID.randomUUID().toString())
        stateFlow.value += id to data
        return success(Project(id, data))
    }

    override suspend fun read(filters: ProjectFilters): RepositoryResult<Flow<List<Project>>> {
        val projects = stateFlow.map { projects ->
            projects
                .map { (id, data) -> Project(id, data) }
                .filter { project -> filters satisfies project }
        }
        return success(projects)
    }

    override suspend fun update(id: ProjectId, update: UpdateProject): RepositoryResult<Project> {
        delay(2.seconds)

        val data = stateFlow.value[id]
        if (data == null) {
            val cause = IllegalStateException("""No project found with identifier "$id"""")
            return error(BaseException.Unknown(cause))
        }

        val updated = data.copy(
            workspace = update.workspace ?: data.workspace,
            name = update.name ?: data.name,
            description = update.description ?: data.description,
            visibility = update.visibility ?: data.visibility,
            image = update.image ?: data.image,
        )
        stateFlow.value += id to updated
        return success(Project(id, updated))
    }

    override suspend fun delete(id: ProjectId): RepositoryResult<Project> {
        delay(2.seconds)

        val data = stateFlow.value[id]
        if (data == null) {
            val cause = IllegalStateException("""No project found with identifier "$id"""")
            return error(BaseException.Unknown(cause))
        }

        stateFlow.value -= id
        return success(Project(id, data))
    }

    private val stateFlow = MutableStateFlow(
        value = mapOf(
            ProjectId("1") to ProjectData(
                workspace = WorkspaceId("1"),
                name = "Stream ECS",
                description = "ECS created with safety in mind",
                visibility = Visibility.Private,
                image = null,
            ),
        ),
    )
}
