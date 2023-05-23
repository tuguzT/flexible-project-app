package io.github.tuguzt.flexibleproject.data.repository.user.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique

@Entity
internal data class UserEntity(
    @Id var id: Long = 0,
    @Unique var uid: String? = null,
    @Unique val name: String? = null,
    val displayName: String? = null,
    val role: String? = null,
    @Unique val email: String? = null,
    val avatar: String? = null,
)
