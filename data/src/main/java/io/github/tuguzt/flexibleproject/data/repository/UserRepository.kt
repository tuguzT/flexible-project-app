package io.github.tuguzt.flexibleproject.data.repository

import io.github.tuguzt.flexibleproject.data.datasource.UserDataSource
import io.github.tuguzt.flexibleproject.domain.model.User

data class UserRepository(val dataSource: UserDataSource) : Repository<User>
