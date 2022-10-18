package io.github.tuguzt.flexibleproject.data.interactor

import io.github.tuguzt.flexibleproject.domain.usecase.PasswordVerifier as DomainPasswordVerifier

class PasswordVerifier : DomainPasswordVerifier {
    private companion object {
        val regex: Regex =
            Regex("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?\\d)(?=.*?[()#?!@$%^&*_\\-]).{8,}$")
    }

    override fun verify(password: String): Boolean = regex matches password
}
