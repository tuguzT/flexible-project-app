package io.github.tuguzt.flexibleproject.viewmodel.basic.home

import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.FilterWorkspaces
import io.github.tuguzt.flexibleproject.viewmodel.StoreViewModel
import io.github.tuguzt.flexibleproject.viewmodel.basic.home.store.HomeStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.home.store.HomeStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.home.store.HomeStore.State
import io.github.tuguzt.flexibleproject.viewmodel.basic.home.store.HomeStoreProvider
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    workspaces: FilterWorkspaces,
    storeFactory: StoreFactory,
) : StoreViewModel<Intent, State, Label>() {
    override val provider = HomeStoreProvider(
        workspaces = workspaces,
        storeFactory = storeFactory,
        coroutineContext = viewModelScope.coroutineContext,
    )
}
