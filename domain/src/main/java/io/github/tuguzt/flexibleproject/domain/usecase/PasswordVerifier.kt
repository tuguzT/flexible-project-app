package io.github.tuguzt.flexibleproject.domain.usecase

public interface PasswordVerifier {
    public fun verify(password: String): Boolean
}
