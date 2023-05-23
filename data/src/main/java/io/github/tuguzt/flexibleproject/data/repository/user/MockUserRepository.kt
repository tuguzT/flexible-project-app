package io.github.tuguzt.flexibleproject.data.repository.user

import io.github.tuguzt.flexibleproject.domain.model.user.Name
import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.user.UpdateUser
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserCredentials
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.domain.model.user.UserFilters
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository
import java.util.UUID

class MockUserRepository : UserRepository {
    override suspend fun signIn(credentials: UserCredentials): User {
        val name = credentials.name
        val user = users.asSequence().find { (_, data) -> data.name == name }
        checkNotNull(user) { """No user found with name "$name"""" }

        val (id, data) = user
        return User(id, data)
    }

    override suspend fun signUp(credentials: UserCredentials): User {
        val name = credentials.name
        checkUniqueName(name)

        val id = UserId(UUID.randomUUID().toString())
        val data = UserData(
            name = name,
            displayName = name,
            role = Role.User,
            email = null,
            avatar = null,
        )
        users[id] = data
        return User(id, data)
    }

    override suspend fun signOut(id: UserId): User {
        val data = users[id]
        checkNotNull(data) { """No user found with identifier "$id"""" }
        return User(id, data)
    }

    override suspend fun read(filters: UserFilters): List<User> =
        users.asSequence()
            .map { (id, data) -> User(id, data) }
            .filter { user -> filters satisfies user }
            .toList()

    override suspend fun update(id: UserId, update: UpdateUser): User {
        val data = users[id]
        checkNotNull(data) { """No user found with identifier "$id"""" }

        val updated = data.copy(
            name = update.name ?: data.name,
            displayName = update.displayName ?: data.displayName,
            email = update.email ?: data.email,
            avatar = update.avatar ?: data.avatar,
        )
        users[id] = updated
        return User(id, updated)
    }

    override suspend fun delete(id: UserId): User {
        val data = users.remove(id)
        checkNotNull(data) { """No user found with identifier "$id"""" }
        return User(id, data)
    }

    private fun checkUniqueName(name: Name) {
        val data = users.values.find { data -> data.name == name }
        check(data == null) { """User with name "$name" already exists""" }
    }

    private val users = mutableMapOf(
        UserId("timur") to UserData(
            name = "tuguzT",
            displayName = "Timur Tugushev",
            role = Role.Administrator,
            email = "timurka.tugushev@gmail.com",
            avatar = "https://avatars.githubusercontent.com/u/56771526",
        ),
    )
}
