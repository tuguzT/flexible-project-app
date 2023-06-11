package io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store

import com.arkivanov.mvikotlin.core.store.Store
import io.github.tuguzt.flexibleproject.domain.model.project.Project
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.WorkspaceStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.WorkspaceStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.WorkspaceStore.State

interface WorkspaceStore : Store<Intent, State, Label> {
    sealed interface Intent {
        data class Load(val id: WorkspaceId) : Intent
        object Delete : Intent
    }

    data class State(
        val workspace: WorkspaceWithProjects?,
        val loading: Boolean,
    ) {
        data class WorkspaceWithProjects(
            val workspace: Workspace,
            val projects: List<Project>,
        )
    }

    sealed interface Label {
        data class NotFound(val id: WorkspaceId) : Label
        data class WorkspaceDeleted(val workspace: Workspace) : Label
        object LocalStoreError : Label
        object NetworkAccessError : Label
        object UnknownError : Label
    }
}
