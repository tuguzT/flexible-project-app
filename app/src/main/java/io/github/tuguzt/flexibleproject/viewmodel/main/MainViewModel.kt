package io.github.tuguzt.flexibleproject.viewmodel.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mu.KotlinLogging
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private var _uiState by mutableStateOf(MainViewModelState())
    val uiState get() = _uiState

    fun updateTitle(title: String) {
        _uiState = uiState.copy(title = title)
    }

    fun updateOnNavigationIconAction(onNavigationIconAction: () -> Unit) {
        _uiState = uiState.copy(onNavigationIconAction = onNavigationIconAction)
    }
}
