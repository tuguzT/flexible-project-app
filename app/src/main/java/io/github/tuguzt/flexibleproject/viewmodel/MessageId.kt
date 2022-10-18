package io.github.tuguzt.flexibleproject.viewmodel

import java.util.UUID

@JvmInline
value class MessageId(val id: UUID) {
    companion object {
        fun random(): MessageId = MessageId(UUID.randomUUID())
    }
}
