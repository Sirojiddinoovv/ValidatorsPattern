package uz.nodir.validatepatterns.service

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import uz.nodir.validatepatterns.model.dto.ChainRequestDTO


/**
@author: Nodir
@date: 06.07.2025
@group: Meloman

 **/
class ChainOfResponsibilityTest {
    private val service = ChainOfResponsibilityService()

    @Nested
    inner class SuccessCases {
        @Test
        fun `validateUser returns true for valid user`() {
            val validUser = ChainRequestDTO(
                username = "johndoe",
                email = "john.doe@example.com",
                age = 30
            )
            assertTrue(service.validateUser(validUser), "Expected true for a fully valid user")
        }
    }

    @Nested
    inner class FailureCases {
        @Test
        fun `validateUser returns false when username is blank`() {
            val user = ChainRequestDTO(
                username = "",
                email = "john.doe@example.com",
                age = 30
            )
            assertFalse(service.validateUser(user), "Expected false when username is blank")
        }

        @Test
        fun `validateUser returns false when email does not contain at symbol`() {
            val user = ChainRequestDTO(
                username = "johndoe",
                email = "johndoe.example.com",
                age = 30
            )
            assertFalse(service.validateUser(user), "Expected false when email is missing '@'")
        }

        @Test
        fun `validateUser returns false when age is below minimum`() {
            val user = ChainRequestDTO(
                username = "johndoe",
                email = "john.doe@example.com",
                age = 17
            )
            assertFalse(service.validateUser(user), "Expected false when age is below 18")
        }

        @Test
        fun `validateUser returns false when age is above maximum`() {
            val user = ChainRequestDTO(
                username = "johndoe",
                email = "john.doe@example.com",
                age = 120
            )
            assertFalse(service.validateUser(user), "Expected false when age is above 99")
        }

        @Test
        fun `validateUser returns false when multiple fields invalid`() {
            val user = ChainRequestDTO(
                username = "",
                email = "invalid_email",
                age = -5
            )
            assertFalse(service.validateUser(user), "Expected false when multiple validations fail")
        }
    }
}