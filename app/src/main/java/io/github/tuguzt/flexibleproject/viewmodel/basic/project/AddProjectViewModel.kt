package io.github.tuguzt.flexibleproject.viewmodel.basic.project

import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.domain.usecase.project.CreateProject
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.FilterWorkspaces
import io.github.tuguzt.flexibleproject.viewmodel.StoreViewModel
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.AddProjectStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.AddProjectStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.AddProjectStore.State
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.AddProjectStoreProvider
import javax.inject.Inject

@HiltViewModel
class AddProjectViewModel @Inject constructor(
    create: CreateProject,
    workspaces: FilterWorkspaces,
    storeFactory: StoreFactory,
) : StoreViewModel<Intent, State, Label>() {
    override val provider = AddProjectStoreProvider(
        create = create,
        workspaces = workspaces,
        storeFactory = storeFactory,
        coroutineContext = viewModelScope.coroutineContext,
    )
}
