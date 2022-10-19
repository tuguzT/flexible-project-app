package io.github.tuguzt.flexibleproject.viewmodel.main.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mu.KotlinLogging
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private var _uiState by mutableStateOf(HomeViewModelState())
    val uiState get() = _uiState
}
