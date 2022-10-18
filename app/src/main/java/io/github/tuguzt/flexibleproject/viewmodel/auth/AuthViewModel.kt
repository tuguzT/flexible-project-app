package io.github.tuguzt.flexibleproject.viewmodel.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.domain.model.UserCredentials
import io.github.tuguzt.flexibleproject.domain.usecase.UserCredentialsVerifier
import io.github.tuguzt.flexibleproject.viewmodel.MessageId
import mu.KotlinLogging
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val credentialsVerifier: UserCredentialsVerifier,
) : ViewModel() {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private var _uiState by mutableStateOf(AuthViewModelState())
    val uiState get() = _uiState

    fun updateUsername(username: String) {
        _uiState = uiState.copy(username = username)
    }

    fun updatePassword(password: String) {
        _uiState = uiState.copy(password = password)
    }

    fun updatePasswordVisible(passwordVisible: Boolean) {
        _uiState = uiState.copy(passwordVisible = passwordVisible)
    }

    fun updateIsLoggedIn(isLoggedIn: Boolean) {
        _uiState = uiState.copy(isLoggedIn = isLoggedIn)
    }

    fun userMessageShown(id: MessageId) {
        val messages = uiState.messages.filterNot { it.id == id }
        _uiState = uiState.copy(messages = messages)
    }

    fun isValidCredentials(): Boolean {
        val credentials = UserCredentials(name = uiState.username, password = uiState.password)
        return credentialsVerifier.verify(credentials)
    }

    fun auth() {
        TODO()
    }

    fun register() {
        TODO()
    }
}
