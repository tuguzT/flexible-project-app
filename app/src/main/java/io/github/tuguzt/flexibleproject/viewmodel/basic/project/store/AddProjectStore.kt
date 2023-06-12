package io.github.tuguzt.flexibleproject.viewmodel.basic.project.store

import com.arkivanov.mvikotlin.core.store.Store
import io.github.tuguzt.flexibleproject.domain.model.project.Project
import io.github.tuguzt.flexibleproject.domain.model.project.Visibility
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.AddProjectStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.AddProjectStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.AddProjectStore.State

interface AddProjectStore : Store<Intent, State, Label> {
    sealed interface Intent {
        data class ChangeWorkspaceFromId(val id: WorkspaceId) : Intent
        data class ChangeName(val name: String) : Intent
        data class ChangeDescription(val description: String) : Intent
        data class ChangeVisibility(val visibility: Visibility) : Intent
        data class ChangeWorkspace(val workspace: Workspace?) : Intent
        object CreateProject : Intent
    }

    data class State(
        val name: String,
        val description: String,
        val visibility: Visibility,
        val allWorkspaces: List<Workspace>,
        val workspace: Workspace?,
        val loading: Boolean,
        val valid: Boolean,
    )

    sealed interface Label {
        data class ProjectCreated(val project: Project) : Label
        object LocalStoreError : Label
        object NetworkAccessError : Label
        object UnknownError : Label
    }
}
