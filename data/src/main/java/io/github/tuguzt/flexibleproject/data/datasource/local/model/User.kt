package io.github.tuguzt.flexibleproject.data.datasource.local.model

import io.github.tuguzt.flexibleproject.data.datasource.local.model.converter.UserRoleConverter
import io.github.tuguzt.flexibleproject.domain.model.UserRole
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique

@Entity
internal data class User(
    @Id var id: Long,
    @Unique var remoteId: String,
    @Unique var name: String,
    @Unique var email: String?,
    @Convert(converter = UserRoleConverter::class, dbType = Int::class)
    var role: UserRole,
)
