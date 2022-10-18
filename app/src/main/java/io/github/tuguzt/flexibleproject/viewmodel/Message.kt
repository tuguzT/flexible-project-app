package io.github.tuguzt.flexibleproject.viewmodel

data class Message<T : MessageKind>(
    val kind: T,
    val id: MessageId = MessageId.random(),
)
