package io.github.tuguzt.flexibleproject.data.repository.user

import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.domain.model.user.UserFilters
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository

class MockUserRepository : UserRepository {
    override suspend fun read(filters: UserFilters): List<User> =
        users.asSequence()
            .map { (id, data) -> User(id, data) }
            .filter { user -> filters satisfies user }
            .toList()

    private val users = mapOf(
        UserId("timur") to UserData(
            name = "tuguzT",
            displayName = "Timur Tugushev",
            role = Role.User,
            email = "timurka.tugushev@gmail.com",
            avatar = "https://avatars.githubusercontent.com/u/56771526",
        ),
    )
}
