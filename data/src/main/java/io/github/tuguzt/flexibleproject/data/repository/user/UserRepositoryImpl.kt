package io.github.tuguzt.flexibleproject.data.repository.user

import io.github.tuguzt.flexibleproject.data.LocalClient
import io.github.tuguzt.flexibleproject.data.RemoteClient
import io.github.tuguzt.flexibleproject.data.repository.user.datasource.LocalUserDataSource
import io.github.tuguzt.flexibleproject.data.repository.user.datasource.RemoteUserDataSource
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserFilters
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository

class UserRepositoryImpl(local: LocalClient, remote: RemoteClient) : UserRepository {
    private val local = LocalUserDataSource(local)
    private val remote = RemoteUserDataSource(remote)

    override suspend fun read(filters: UserFilters): List<User> {
        TODO("Not yet implemented")
    }
}
