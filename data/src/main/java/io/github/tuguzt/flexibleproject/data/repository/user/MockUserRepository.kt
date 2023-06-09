package io.github.tuguzt.flexibleproject.data.repository.user

import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.error
import io.github.tuguzt.flexibleproject.domain.model.success
import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.user.UpdateUser
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserCredentials
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.domain.model.user.UserFilters
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.domain.repository.RepositoryResult
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.UUID
import kotlin.time.Duration.Companion.seconds

class MockUserRepository : UserRepository {
    override suspend fun signIn(credentials: UserCredentials): RepositoryResult<User> {
        delay(2.seconds)

        val name = credentials.name
        val user = usersStateFlow.value.asSequence().find { (_, data) -> data.name == name }
        if (user == null) {
            val cause = IllegalStateException("""No user found with name "$name"""")
            return error(BaseException.Unknown(cause))
        }

        val (id, data) = user
        return success(User(id, data))
    }

    override suspend fun signUp(credentials: UserCredentials): RepositoryResult<User> {
        delay(2.seconds)

        val name = credentials.name
        val data = usersStateFlow.value.values.find { data -> data.name == name }
        if (data != null) {
            val cause = IllegalStateException("""User with name "$name" already exists""")
            return error(BaseException.Unknown(cause))
        }

        val id = UserId(UUID.randomUUID().toString())
        val new = UserData(
            name = name,
            displayName = name,
            role = Role.User,
            email = null,
            avatar = null,
        )
        val users = usersStateFlow.value + (id to new)
        usersStateFlow.emit(users)
        return success(User(id, new))
    }

    override suspend fun signOut(id: UserId): RepositoryResult<User> {
        delay(2.seconds)

        val data = usersStateFlow.value[id]
        if (data == null) {
            val cause = IllegalStateException("""No user found with identifier "$id"""")
            return error(BaseException.Unknown(cause))
        }
        return success(User(id, data))
    }

    override suspend fun read(filters: UserFilters): RepositoryResult<Flow<List<User>>> {
        val users = usersStateFlow.map { users ->
            users.map { (id, data) -> User(id, data) }
                .filter { user -> filters satisfies user }
        }
        return success(users)
    }

    override suspend fun update(id: UserId, update: UpdateUser): RepositoryResult<User> {
        delay(2.seconds)

        val data = usersStateFlow.value[id]
        if (data == null) {
            val cause = IllegalStateException("""No user found with identifier "$id"""")
            return error(BaseException.Unknown(cause))
        }

        val updated = data.copy(
            name = update.name ?: data.name,
            displayName = update.displayName ?: data.displayName,
            email = update.email ?: data.email,
            avatar = update.avatar ?: data.avatar,
        )
        val users = usersStateFlow.value + (id to updated)
        usersStateFlow.emit(users)
        return success(User(id, updated))
    }

    override suspend fun delete(id: UserId): RepositoryResult<User> {
        delay(2.seconds)

        val data = usersStateFlow.value[id]
        if (data == null) {
            val cause = IllegalStateException("""No user found with identifier "$id"""")
            return error(BaseException.Unknown(cause))
        }
        val users = usersStateFlow.value - id
        usersStateFlow.emit(users)
        return success(User(id, data))
    }

    private val usersStateFlow = MutableStateFlow(
        value = mapOf(
            UserId("timur") to UserData(
                name = "tuguzT",
                displayName = "Timur Tugushev",
                role = Role.Administrator,
                email = "timurka.tugushev@gmail.com",
                avatar = "https://avatars.githubusercontent.com/u/56771526",
            ),
        ),
    )
}
