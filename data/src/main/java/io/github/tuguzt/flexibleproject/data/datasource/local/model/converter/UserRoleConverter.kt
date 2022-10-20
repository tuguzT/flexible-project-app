package io.github.tuguzt.flexibleproject.data.datasource.local.model.converter

import io.github.tuguzt.flexibleproject.domain.model.UserRole
import io.objectbox.converter.PropertyConverter

internal class UserRoleConverter : PropertyConverter<UserRole?, Int?> {
    override fun convertToEntityProperty(databaseValue: Int?): UserRole? =
        databaseValue?.let { UserRole.values().getOrNull(it) }

    override fun convertToDatabaseValue(entityProperty: UserRole?): Int? =
        entityProperty?.ordinal
}
