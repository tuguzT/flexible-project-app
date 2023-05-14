package io.github.tuguzt.flexibleproject

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@TestMethodOrder(OrderAnnotation::class)
@DisplayName("Example unit test")
class ExampleUnitTest {
    @Test
    @Order(1)
    @DisplayName("Addition is correct")
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
