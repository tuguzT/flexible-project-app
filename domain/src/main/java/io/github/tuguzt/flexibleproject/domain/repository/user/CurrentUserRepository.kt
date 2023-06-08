package io.github.tuguzt.flexibleproject.domain.repository.user

import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.repository.RepositoryResult
import kotlinx.coroutines.flow.Flow

interface CurrentUserRepository {
    fun read(): Flow<User?>

    suspend fun set(user: User?): RepositoryResult<User?>
}
