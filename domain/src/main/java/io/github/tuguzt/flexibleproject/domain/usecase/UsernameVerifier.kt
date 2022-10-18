package io.github.tuguzt.flexibleproject.domain.usecase

public interface UsernameVerifier {
    public fun verify(username: String): Boolean
}
