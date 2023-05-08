package io.github.tuguzt.flexibleproject.viewmodel.workspace

import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.FindWorkspaceById
import io.github.tuguzt.flexibleproject.viewmodel.StoreViewModel
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore.State
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStoreProvider
import javax.inject.Inject

@HiltViewModel
class WorkspaceViewModel @Inject constructor(
    findById: FindWorkspaceById,
    storeFactory: StoreFactory,
) : StoreViewModel<Intent, State, Label>() {
    private val provider = WorkspaceStoreProvider(
        findById = findById,
        storeFactory = storeFactory,
        coroutineContext = viewModelScope.coroutineContext,
    )
    override val store: WorkspaceStore = provider.provide()
}
