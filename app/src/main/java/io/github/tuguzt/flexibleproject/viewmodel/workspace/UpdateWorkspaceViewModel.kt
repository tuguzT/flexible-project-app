package io.github.tuguzt.flexibleproject.viewmodel.workspace

import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.FilterWorkspaces
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.UpdateWorkspace
import io.github.tuguzt.flexibleproject.viewmodel.StoreViewModel
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.UpdateWorkspaceStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.UpdateWorkspaceStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.UpdateWorkspaceStore.State
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.UpdateWorkspaceStoreProvider
import javax.inject.Inject

@HiltViewModel
class UpdateWorkspaceViewModel @Inject constructor(
    workspaces: FilterWorkspaces,
    update: UpdateWorkspace,
    storeFactory: StoreFactory,
) : StoreViewModel<Intent, State, Label>() {
    override val provider = UpdateWorkspaceStoreProvider(
        workspaces = workspaces,
        update = update,
        storeFactory = storeFactory,
        coroutineContext = viewModelScope.coroutineContext,
    )
}
