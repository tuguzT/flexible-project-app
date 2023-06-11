package io.github.tuguzt.flexibleproject.viewmodel.basic.workspace

import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.domain.usecase.project.FilterProjects
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.DeleteWorkspace
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.FilterWorkspaces
import io.github.tuguzt.flexibleproject.viewmodel.StoreViewModel
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.WorkspaceStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.WorkspaceStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.WorkspaceStore.State
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.WorkspaceStoreProvider
import javax.inject.Inject

@HiltViewModel
class WorkspaceViewModel @Inject constructor(
    workspaces: FilterWorkspaces,
    projects: FilterProjects,
    delete: DeleteWorkspace,
    storeFactory: StoreFactory,
) : StoreViewModel<Intent, State, Label>() {
    override val provider = WorkspaceStoreProvider(
        workspaces = workspaces,
        projects = projects,
        delete = delete,
        storeFactory = storeFactory,
        coroutineContext = viewModelScope.coroutineContext,
    )
}
