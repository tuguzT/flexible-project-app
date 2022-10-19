package io.github.tuguzt.flexibleproject.viewmodel.main.home

import io.github.tuguzt.flexibleproject.domain.model.Board
import io.github.tuguzt.flexibleproject.viewmodel.Message
import io.github.tuguzt.flexibleproject.viewmodel.MessageState

data class HomeViewModelState(
    val boards: List<Board> = listOf(),
    val isRefreshing: Boolean = true,
    override val messages: List<Message<HomeViewModelMessageKind>> = listOf(),
) : MessageState<HomeViewModelMessageKind>
