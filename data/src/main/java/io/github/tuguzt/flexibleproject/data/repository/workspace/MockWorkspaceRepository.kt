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
    override suspend fun allFlow(): RepositoryResult<Flow<List<Workspace>>> {
        val flow = workspacesStateFlow.map { workspaces ->
            workspaces.map { (id, data) -> Workspace(id, data) }
        }
        return success(flow)
    }

    override suspend fun create(data: WorkspaceData): RepositoryResult<Workspace> {
        delay(2.seconds)

        val id = WorkspaceId(UUID.randomUUID().toString())
        workspaces[id] = data
        workspacesStateFlow.emit(workspaces)
        return success(Workspace(id, data))
    }

    override suspend fun read(filters: WorkspaceFilters): RepositoryResult<List<Workspace>> {
        val workspaces = workspaces.asSequence()
            .map { (id, data) -> Workspace(id, data) }
            .filter { workspace -> filters satisfies workspace }
            .toList()
        return success(workspaces)
    }

    override suspend fun update(
        id: WorkspaceId,
        update: UpdateWorkspace,
    ): RepositoryResult<Workspace> {
        delay(2.seconds)

        val data = workspaces[id]
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
        workspaces[id] = updated
        workspacesStateFlow.emit(workspaces)
        return success(Workspace(id, updated))
    }

    override suspend fun delete(id: WorkspaceId): RepositoryResult<Workspace> {
        delay(2.seconds)

        val data = workspaces.remove(id)
        if (data == null) {
            val cause = IllegalStateException("""No workspace found with identifier "$id"""")
            return error(BaseException.Unknown(cause))
        }
        workspacesStateFlow.emit(workspaces)
        return success(Workspace(id, data))
    }

    private val workspaces: MutableMap<WorkspaceId, WorkspaceData> = mutableMapOf(
        WorkspaceId("1") to WorkspaceData(
            name = "First workspace",
            description = "Empty workspace",
            visibility = Visibility.Public,
            image = "https://avatars.githubusercontent.com/u/56771526",
        ),
        WorkspaceId("2") to WorkspaceData(
            name = "Second workspace",
            description = "Empty workspace",
            visibility = Visibility.Public,
            image = null,
        ),
    )
    private val workspacesStateFlow = MutableStateFlow(workspaces)
}
