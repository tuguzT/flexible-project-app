package io.github.tuguzt.flexibleproject.viewmodel.basic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.ReadAllWorkspaces
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStore
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStore.State
import io.github.tuguzt.flexibleproject.viewmodel.basic.store.BasicStoreProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BasicViewModel @Inject constructor(
    readAll: ReadAllWorkspaces,
    storeFactory: StoreFactory,
) : ViewModel() {
    private val provider = BasicStoreProvider(
        readAll = readAll,
        storeFactory = storeFactory,
        coroutineContext = viewModelScope.coroutineContext,
    )
    private val store: BasicStore = provider.provide()

    fun accept(intent: Intent): Unit = store.accept(intent)

    val stateFlow: StateFlow<State> = store.stateFlow(scope = viewModelScope)

    val labels: Flow<Label> = store.labels
}
