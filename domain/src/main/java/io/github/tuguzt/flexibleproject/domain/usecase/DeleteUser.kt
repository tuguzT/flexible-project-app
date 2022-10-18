package io.github.tuguzt.flexibleproject.domain.usecase

import io.github.tuguzt.flexibleproject.domain.model.Id
import io.github.tuguzt.flexibleproject.domain.model.User

public interface DeleteUser {
    public suspend fun delete(id: Id<User>): User?
}
