package io.github.tuguzt.flexibleproject.data.repository.user

import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository

class MockUserRepository : UserRepository {
    override suspend fun read(id: UserId): User {
        val data = UserData(
            name = id.toString(),
            displayName = "User with ID $id",
            role = Role.User,
            email = "timurka.tugushev@gmail.com",
            avatarUrl = "https://avatars.githubusercontent.com/u/56771526",
        )
        return User(id = id, data = data)
    }
}
