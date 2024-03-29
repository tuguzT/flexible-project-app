package io.github.tuguzt.flexibleproject.data.repository.workspace

import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.error
import io.github.tuguzt.flexibleproject.domain.model.success
import io.github.tuguzt.flexibleproject.domain.model.workspace.UpdateWorkspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceData
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceFilters
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.domain.repository.RepositoryResult
import io.github.tuguzt.flexibleproject.domain.repository.workspace.WorkspaceRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.UUID
import kotlin.time.Duration.Companion.seconds

class MockWorkspaceRepository : WorkspaceRepository {
    override suspend fun create(data: WorkspaceData): RepositoryResult<Workspace> {
        delay(2.seconds)

        val id = WorkspaceId(UUID.randomUUID().toString())
        stateFlow.value += id to data
        return success(Workspace(id, data))
    }

    override suspend fun read(filters: WorkspaceFilters): RepositoryResult<Flow<List<Workspace>>> {
        val workspaces = stateFlow.map { workspaces ->
            workspaces
                .map { (id, data) -> Workspace(id, data) }
                .filter { workspace -> filters satisfies workspace }
        }
        return success(workspaces)
    }

    override suspend fun update(
        id: WorkspaceId,
        update: UpdateWorkspace,
    ): RepositoryResult<Workspace> {
        delay(2.seconds)

        val data = stateFlow.value[id]
        if (data == null) {
            val cause = IllegalStateException("""No workspace found with identifier "$id"""")
            return error(BaseException.Unknown(cause))
        }

        val updated = data.copy(
            name = update.name ?: data.name,
            description = update.description ?: data.description,
            visibility = update.visibility ?: data.visibility,
            image = update.image ?: data.image,
        )
        stateFlow.value += id to updated
        return success(Workspace(id, updated))
    }

    override suspend fun delete(id: WorkspaceId): RepositoryResult<Workspace> {
        delay(2.seconds)

        val data = stateFlow.value[id]
        if (data == null) {
            val cause = IllegalStateException("""No workspace found with identifier "$id"""")
            return error(BaseException.Unknown(cause))
        }

        stateFlow.value -= id
        return success(Workspace(id, data))
    }

    private val stateFlow = MutableStateFlow(
        value = mapOf(
            WorkspaceId("1") to WorkspaceData(
                name = "Toucan Games",
                description = "Organization for projects linked to Toucan game engine",
                visibility = Visibility.Private,
                image = "https://avatars.githubusercontent.com/u/96529093",
            ),
            WorkspaceId("2") to WorkspaceData(
                name = "Test workspace",
                description = "Test workspace description",
                visibility = Visibility.Public,
                image = null,
            ),
        ),
    )
}
