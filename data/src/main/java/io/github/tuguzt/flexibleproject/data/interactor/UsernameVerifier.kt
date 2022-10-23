package io.github.tuguzt.flexibleproject.data.interactor

import io.github.tuguzt.flexibleproject.domain.Result
import io.github.tuguzt.flexibleproject.domain.success
import io.github.tuguzt.flexibleproject.domain.usecase.UsernameVerifier.Error
import io.github.tuguzt.flexibleproject.domain.usecase.UsernameVerifier as DomainUsernameVerifier

class UsernameVerifier : DomainUsernameVerifier {
    private companion object {
        val regex: Regex =
            Regex("^(?=.{4,32}$)(?![-_.])(?!.*[-_.]{2})[a-zA-Z\\d-_.]+(?<![-_.])$")
    }

    override fun verify(username: String): Result<Boolean, Error> =
        success(regex matches username)
}
