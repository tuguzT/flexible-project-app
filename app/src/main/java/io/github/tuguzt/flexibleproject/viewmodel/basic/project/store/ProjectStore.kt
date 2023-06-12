package io.github.tuguzt.flexibleproject.viewmodel.basic.project.store

import com.arkivanov.mvikotlin.core.store.Store
import io.github.tuguzt.flexibleproject.domain.model.project.Project
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectId
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.ProjectStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.ProjectStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.ProjectStore.State

interface ProjectStore : Store<Intent, State, Label> {
    sealed interface Intent {
        data class Load(val id: ProjectId) : Intent
        object Delete : Intent
    }

    data class State(
        val project: Project?,
        val loading: Boolean,
    )

    sealed interface Label {
        data class NotFound(val id: ProjectId) : Label
        data class ProjectDeleted(val project: Project) : Label
        object LocalStoreError : Label
        object NetworkAccessError : Label
        object UnknownError : Label
    }
}
