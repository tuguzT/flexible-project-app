package io.github.tuguzt.flexibleproject.data.repository.user

import io.github.tuguzt.flexibleproject.domain.model.success
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.repository.RepositoryResult
import io.github.tuguzt.flexibleproject.domain.repository.user.CurrentUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MockCurrentUserRepository : CurrentUserRepository {
    override fun read(): Flow<User?> = stateFlow.asStateFlow()

    override suspend fun set(user: User?): RepositoryResult<User?> {
        stateFlow.value = user
        return success(user)
    }

    private val stateFlow: MutableStateFlow<User?> = MutableStateFlow(null)
}
