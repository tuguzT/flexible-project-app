package io.github.tuguzt.flexibleproject.viewmodel.basic.home.store

import com.arkivanov.mvikotlin.core.store.Store
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.viewmodel.basic.home.store.HomeStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.home.store.HomeStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.home.store.HomeStore.State

interface HomeStore : Store<Intent, State, Label> {
    sealed interface Intent {
        object Load : Intent
        data class WorkspacesExpand(val expanded: Boolean) : Intent
    }

    data class State(
        val workspaces: List<Workspace>,
        val workspacesExpanded: Boolean,
        val loading: Boolean,
    )

    sealed interface Label {
        object LocalStoreError : Label
        object NetworkAccessError : Label
        object UnknownError : Label
    }
}
