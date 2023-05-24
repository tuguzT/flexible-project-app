package io.github.tuguzt.flexibleproject.data.repository.user

import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.repository.user.CurrentUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MockCurrentUserRepository : CurrentUserRepository {
    override val currentUserFlow: StateFlow<User?>
        get() = _currentUserFlow.asStateFlow()

    override fun setCurrentUser(user: User?) {
        _currentUserFlow.value = user
    }

    private val _currentUserFlow: MutableStateFlow<User?> = MutableStateFlow(null)
}
