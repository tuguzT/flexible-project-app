package io.github.tuguzt.flexibleproject.viewmodel

interface MessageState<T : MessageKind> : UiState {
    val messages: List<Message<T>>
}
