package io.github.tuguzt.flexibleproject.domain.repository.user

import io.github.tuguzt.flexibleproject.domain.model.user.User
import kotlinx.coroutines.flow.StateFlow

interface CurrentUserRepository {
    val currentUserFlow: StateFlow<User?>

    fun setCurrentUser(user: User?)
}
