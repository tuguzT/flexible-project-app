package io.github.tuguzt.flexibleproject.viewmodel.workspace

import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.CreateWorkspace
import io.github.tuguzt.flexibleproject.viewmodel.StoreViewModel
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.AddWorkspaceStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.AddWorkspaceStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.AddWorkspaceStore.State
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.AddWorkspaceStoreProvider
import javax.inject.Inject

@HiltViewModel
class AddWorkspaceViewModel @Inject constructor(
    create: CreateWorkspace,
    storeFactory: StoreFactory,
) : StoreViewModel<Intent, State, Label>() {
    override val provider = AddWorkspaceStoreProvider(
        create = create,
        storeFactory = storeFactory,
        coroutineContext = viewModelScope.coroutineContext,
    )
}
