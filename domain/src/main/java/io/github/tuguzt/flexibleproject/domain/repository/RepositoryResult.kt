package io.github.tuguzt.flexibleproject.domain.repository

import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result

typealias RepositoryResult<T> = Result<T, BaseException>
