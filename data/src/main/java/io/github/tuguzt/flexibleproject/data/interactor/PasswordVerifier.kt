package io.github.tuguzt.flexibleproject.data.interactor

import io.github.tuguzt.flexibleproject.domain.Result
import io.github.tuguzt.flexibleproject.domain.success
import io.github.tuguzt.flexibleproject.domain.usecase.PasswordVerifier.Error
import io.github.tuguzt.flexibleproject.domain.usecase.PasswordVerifier as DomainPasswordVerifier

class PasswordVerifier : DomainPasswordVerifier {
    private companion object {
        val regex: Regex =
            Regex("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?\\d)(?=.*?[()#?!@$%^&*_\\-]).{8,}$")
    }

    override fun verify(password: String): Result<Boolean, Error> =
        success(regex matches password)
}
