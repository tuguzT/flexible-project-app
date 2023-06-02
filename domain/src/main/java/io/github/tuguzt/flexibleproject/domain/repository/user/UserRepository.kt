package io.github.tuguzt.flexibleproject.domain.repository.user

import io.github.tuguzt.flexibleproject.domain.model.user.UpdateUser
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserCredentials
import io.github.tuguzt.flexibleproject.domain.model.user.UserFilters
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.domain.repository.RepositoryResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun signIn(credentials: UserCredentials): RepositoryResult<User>

    suspend fun signUp(credentials: UserCredentials): RepositoryResult<User>

    suspend fun signOut(id: UserId): RepositoryResult<User>

    suspend fun read(filters: UserFilters): RepositoryResult<Flow<List<User>>>

    suspend fun update(id: UserId, update: UpdateUser): RepositoryResult<User>

    suspend fun delete(id: UserId): RepositoryResult<User>
}
