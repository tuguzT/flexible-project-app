package io.github.tuguzt.flexibleproject.domain.model

public data class UserFilters(val ids: List<Id<User>>) {
    public fun isEmpty(): Boolean = ids.isEmpty()
}
