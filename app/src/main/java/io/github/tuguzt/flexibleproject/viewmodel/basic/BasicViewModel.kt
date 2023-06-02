package io.github.tuguzt.flexibleproject.viewmodel.basic

import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.FilterWorkspaces
import io.github.tuguzt.flexibleproject.viewmodel.StoreViewModel
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStore.State
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStoreProvider
import javax.inject.Inject

@HiltViewModel
class BasicViewModel @Inject constructor(
    workspaces: FilterWorkspaces,
    storeFactory: StoreFactory,
) : StoreViewModel<Intent, State, Label>() {
    override val provider = BasicStoreProvider(
        workspaces = workspaces,
        storeFactory = storeFactory,
        coroutineContext = viewModelScope.coroutineContext,
    )
}
