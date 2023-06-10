package io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store

import com.arkivanov.mvikotlin.core.store.Store
import io.github.tuguzt.flexibleproject.domain.model.workspace.Description
import io.github.tuguzt.flexibleproject.domain.model.workspace.Image
import io.github.tuguzt.flexibleproject.domain.model.workspace.Name
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.UpdateWorkspaceStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.UpdateWorkspaceStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.UpdateWorkspaceStore.State

interface UpdateWorkspaceStore : Store<Intent, State, Label> {
    sealed interface Intent {
        data class Load(val id: WorkspaceId) : Intent
        data class ChangeName(val name: Name) : Intent
        data class ChangeDescription(val description: Description) : Intent
        data class ChangeVisibility(val visibility: Visibility) : Intent
        data class ChangeImage(val image: Image?) : Intent
        object UpdateWorkspace : Intent
    }

    data class State(
        val name: Name,
        val description: Description,
        val visibility: Visibility,
        val image: Image?,
        val valid: Boolean,
        val loading: Boolean,
    )

    sealed interface Label {
        data class NotFound(val id: WorkspaceId) : Label
        object WorkspaceUpdated : Label
        object LocalStoreError : Label
        object NetworkAccessError : Label
        object UnknownError : Label
    }
}
