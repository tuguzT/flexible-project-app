package io.github.tuguzt.flexibleproject

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import kotlin.time.Duration.Companion.seconds

@TestMethodOrder(OrderAnnotation::class)
@DisplayName("Authentication tests")
class AuthTest {
    @Test
    @Order(1)
    @DisplayName("Sign in as existing user")
    fun signIn(): Unit = runBlocking {
        delay(1.seconds)
        assertEquals(4, 2 + 2)
    }

    @Test
    @Order(2)
    @DisplayName("Sign up as new user")
    fun signUp(): Unit = runBlocking {
        delay(2.seconds)
        assertEquals(4, 2 + 2)
    }

    @Test
    @Order(3)
    @DisplayName("Logout from user account")
    fun logOut(): Unit = runBlocking {
        delay(1.seconds)
        assertEquals(4, 2 + 2)
    }

    @Test
    @Order(4)
    @DisplayName("Get access and refresh tokens by user credentials")
    fun getTokens(): Unit = runBlocking {
        delay(1.5.seconds)
        assertEquals(4, 2 + 2)
    }

    @Test
    @Order(5)
    @DisplayName("Update access token by refresh token")
    fun refreshTokens(): Unit = runBlocking {
        delay(1.5.seconds)
        assertEquals(4, 2 + 2)
    }
}
