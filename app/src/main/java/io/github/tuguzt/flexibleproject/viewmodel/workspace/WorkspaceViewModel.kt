package io.github.tuguzt.flexibleproject.viewmodel.workspace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStore.State
import io.github.tuguzt.flexibleproject.viewmodel.workspace.store.WorkspaceStoreProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class WorkspaceViewModel @Inject constructor(
    storeFactory: StoreFactory,
) : ViewModel() {
    private val provider = WorkspaceStoreProvider(
        storeFactory = storeFactory,
        coroutineContext = viewModelScope.coroutineContext,
    )
    private val store: WorkspaceStore = provider.provide()

    fun accept(intent: Intent): Unit = store.accept(intent)

    val stateFlow: StateFlow<State> = store.stateFlow(scope = viewModelScope)

    val labels: Flow<Label> = store.labels
}
