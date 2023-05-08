package io.github.tuguzt.flexibleproject.viewmodel.basic.store

import com.arkivanov.mvikotlin.core.store.Store
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStore.State

interface BasicStore : Store<Intent, State, Label> {
    sealed interface Intent {
        object Load : Intent
    }

    data class State(
        val workspaces: List<Workspace>,
        val isLoading: Boolean,
    )

    sealed interface Label
}
