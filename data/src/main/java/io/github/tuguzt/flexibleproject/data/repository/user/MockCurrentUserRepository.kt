package io.github.tuguzt.flexibleproject.data.repository.user

import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
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

    // TODO make null when auth flow will be implemented
    private val _currentUserFlow: MutableStateFlow<User?> = MutableStateFlow(
        User(
            id = UserId("timur"),
            data = UserData(
                name = "tuguzT",
                displayName = "Timur Tugushev",
                role = Role.Administrator,
                email = "timurka.tugushev@gmail.com",
                avatar = "https://avatars.githubusercontent.com/u/56771526",
            ),
        ),
    )
}
