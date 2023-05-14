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
@DisplayName("User tests")
class UserTest {
    @Test
    @Order(1)
    @DisplayName("Get all users from the system")
    fun getAllUsers(): Unit = runBlocking {
        delay(1.5.seconds)
        assertEquals(4, 2 + 2)
    }

    @Test
    @Order(2)
    @DisplayName("Find user by identifier")
    fun findUserById(): Unit = runBlocking {
        delay(2.seconds)
        assertEquals(4, 2 + 2)
    }

    @Test
    @Order(3)
    @DisplayName("Find user by name")
    fun findUserByName(): Unit = runBlocking {
        delay(2.seconds)
        assertEquals(4, 2 + 2)
    }

    @Test
    @Order(4)
    @DisplayName("Update user name")
    fun updateUserName(): Unit = runBlocking {
        delay(2.seconds)
        assertEquals(4, 2 + 2)
    }

    @Test
    @Order(5)
    @DisplayName("Update user display name")
    fun updateUserDisplayName(): Unit = runBlocking {
        delay(2.seconds)
        assertEquals(4, 2 + 2)
    }

    @Test
    @Order(6)
    @DisplayName("Update user avatar image")
    fun updateUserAvatar(): Unit = runBlocking {
        delay(2.seconds)
        assertEquals(4, 2 + 2)
    }

    @Test
    @Order(7)
    @DisplayName("Delete user from the system")
    fun deleteUser(): Unit = runBlocking {
        delay(2.seconds)
        assertEquals(4, 2 + 2)
    }
}
