package io.github.tuguzt.flexibleproject.domain.usecase

import io.github.tuguzt.flexibleproject.domain.Result

public interface UsernameVerifier {
    /**
     * Checks if the provided [username] meets all requirements of the module.
     *
     * Requirements are:
     * - must be from 4 to 32 characters in length
     * - must contain latin or `-`, `_`, `.` characters
     * - must not start or end with `-`, `_`, `.` characters
     * - `-`, `_`, `.` characters can't be next to each other
     * - `-`, `_`, `.` characters can't be used multiple times in a row
     */
    public fun verify(username: String): Result<Boolean, Error>

    public class Error(override val message: String? = null) : Exception(message)
}
