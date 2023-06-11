package io.github.tuguzt.flexibleproject.viewmodel.basic.home.store

import com.arkivanov.mvikotlin.core.store.Store
import io.github.tuguzt.flexibleproject.domain.model.project.Project
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.viewmodel.basic.home.store.HomeStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.home.store.HomeStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.home.store.HomeStore.State

interface HomeStore : Store<Intent, State, Label> {
    sealed interface Intent {
        data class WorkspacesExpand(val expanded: Boolean) : Intent
    }

    data class State(
        val workspaces: List<WorkspaceWithProjects>,
        val workspacesExpanded: Boolean,
        val loading: Boolean,
    ) {
        data class WorkspaceWithProjects(
            val workspace: Workspace,
            val projects: List<Project>,
        )
    }

    sealed interface Label {
        object LocalStoreError : Label
        object NetworkAccessError : Label
        object UnknownError : Label
    }
}
