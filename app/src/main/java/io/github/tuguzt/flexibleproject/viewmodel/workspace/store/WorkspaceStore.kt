package io.github.tuguzt.flexibleproject.viewmodel.workspace.store

import com.arkivanov.mvikotlin.core.store.Store
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore.State

interface WorkspaceStore : Store<Intent, State, Label> {
    sealed interface Intent {
        data class Load(val id: WorkspaceId) : Intent
    }

    data class State(
        val workspace: Workspace?,
        val isLoading: Boolean,
    )

    sealed interface Label {
        object LoadError : Label
    }
}
