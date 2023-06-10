package io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store

import com.arkivanov.mvikotlin.core.store.Store
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.AddWorkspaceStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.AddWorkspaceStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.AddWorkspaceStore.State

interface AddWorkspaceStore : Store<Intent, State, Label> {
    sealed interface Intent {
        data class ChangeName(val name: String) : Intent
        data class ChangeDescription(val description: String) : Intent
        data class ChangeVisibility(val visibility: Visibility) : Intent
        object CreateWorkspace : Intent
    }

    data class State(
        val name: String,
        val description: String,
        val visibility: Visibility,
        val loading: Boolean,
        val valid: Boolean,
    )

    sealed interface Label {
        data class WorkspaceCreated(val workspace: Workspace) : Label
        object LocalStoreError : Label
        object NetworkAccessError : Label
        object UnknownError : Label
    }
}
