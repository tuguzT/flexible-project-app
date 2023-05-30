package io.github.tuguzt.flexibleproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

abstract class StoreViewModel<in Intent : Any, out State : Any, out Label : Any> : ViewModel() {
    protected abstract val provider: StoreProvider<Intent, State, Label>

    protected val store by lazy { provider.provide() }

    fun accept(intent: Intent): Unit = store.accept(intent)

    val stateFlow: StateFlow<State>
        get() = store.stateFlow(scope = viewModelScope)

    val labels: Flow<Label>
        get() = store.labels
}
