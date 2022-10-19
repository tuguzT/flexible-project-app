package io.github.tuguzt.flexibleproject.viewmodel.main.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.domain.model.Board
import io.github.tuguzt.flexibleproject.domain.model.Id
import io.github.tuguzt.flexibleproject.domain.model.User
import io.github.tuguzt.flexibleproject.domain.model.UserRole
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mu.KotlinLogging
import java.util.UUID
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private var _uiState by mutableStateOf(HomeViewModelState())
    val uiState get() = _uiState

    private val owner = User(
        id = Id("owner"),
        name = "owner",
        email = null,
        role = UserRole.User,
    )

    init {
        refreshBoards()
    }

    fun refreshBoards() {
        viewModelScope.launch {
            _uiState = uiState.copy(isRefreshing = true)
            logger.debug { "Refreshing boards..." }

            val newBoard = Board(
                id = Id(UUID.randomUUID().toString()),
                name = "Hello World",
                description = "Some long description",
                tasks = listOf(),
                owner = owner,
            )
            val newBoards = uiState.boards + newBoard

            delay(2.seconds)
            logger.debug { "Boards were refreshed!" }
            _uiState = uiState.copy(isRefreshing = false, boards = newBoards)
        }
    }
}
