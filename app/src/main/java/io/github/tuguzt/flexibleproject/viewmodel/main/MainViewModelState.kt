package io.github.tuguzt.flexibleproject.viewmodel.main

import io.github.tuguzt.flexibleproject.viewmodel.UiState

data class MainViewModelState(
    val title: String = "",
    val isTopBarFilled: Boolean = false,
    val onNavigationIconAction: () -> Unit = {},
) : UiState
