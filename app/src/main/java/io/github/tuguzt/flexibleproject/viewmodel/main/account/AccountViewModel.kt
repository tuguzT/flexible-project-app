package io.github.tuguzt.flexibleproject.viewmodel.main.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.viewmodel.MessageId
import mu.KotlinLogging
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor() : ViewModel() {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private var _uiState by mutableStateOf(AccountViewModelState())
    val uiState get() = _uiState

    fun userMessageShown(id: MessageId) {
        val messages = uiState.messages.filterNot { it.id == id }
        _uiState = uiState.copy(messages = messages)
    }
}
