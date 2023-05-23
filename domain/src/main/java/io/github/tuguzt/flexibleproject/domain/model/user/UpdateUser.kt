package io.github.tuguzt.flexibleproject.domain.model.user

data class UpdateUser(
    val name: Name? = null,
    val displayName: DisplayName? = null,
    val email: Email? = null,
    val avatar: Avatar? = null,
)
