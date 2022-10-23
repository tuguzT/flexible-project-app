package io.github.tuguzt.flexibleproject.domain.usecase

import io.github.tuguzt.flexibleproject.domain.Result

public interface PasswordVerifier {
    /**
     * Checks if the provided [password] meets all requirements of the module.
     *
     * Requirements are:
     * - must be from 8 characters in length
     * - must contain at least one upper case latin letter
     * - must contain at least one lower case latin letter
     * - must contain at least one digit
     * - must contain at least one special character: `(`, `)`, `#`, `?`, `!`, `@`, `$`, `%`, `^`, `&`, `*`, `_`, or `-`
     */
    public fun verify(password: String): Result<Boolean, Error>

    public class Error(override val message: String? = null) : Exception(message)
}
