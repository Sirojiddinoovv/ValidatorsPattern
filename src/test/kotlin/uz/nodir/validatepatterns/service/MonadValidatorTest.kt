package uz.nodir.validatepatterns.service

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import uz.nodir.validatepatterns.model.dto.MonadRequestDTO


/**
@author: Nodir
@date: 05.07.2025
@group: Meloman

 **/


class MonadValidatorTest {
    private val service = MonadTestService()

    companion object {
        private const val WORD = "HELLO WORLD!"
    }
    @Nested
    inner class SuccessCases {
        @Test
        fun `validateStr returns true when text equals WORD`() {
            val result = service.validateStr(WORD)
            assertTrue(result, "Expected true for correct text")
        }
    }

    @Nested
    inner class FailureCases {
        @Test
        fun `validateStr returns false when text is empty`() {
            val input = ""
            val result = service.validateStr(input)
            assertFalse(result, "Expected false for empty string")
        }

        @Test
        fun `validateStr returns false when text is too short`() {
            val input = "HELLO"
            val result = service.validateStr(input)
            assertFalse(result, "Expected false for string shorter than 6 characters")
        }

        @Test
        fun `validateStr returns false when text doesn't match WORD`() {
            val input = "HELLO KOTLIN!"
            val result = service.validateStr(input)
            assertFalse(result, "Expected false for text not matching the constant")
        }
    }

    @Nested
    inner class ValidateDTOSuccess {
        @Test
        fun `validateDTO returns true when name is not Nodir and age is positive`() {
            val dto = MonadRequestDTO(name = "John", age = 25)
            assertTrue(service.validateDTO(dto), "Expected true when name is not \"Nodir\" and age is positive")
        }
    }

    @Nested
    inner class ValidateDTOFailure {
        @Test
        fun `validateDTO returns false when name equals Nodir`() {
            val dto = MonadRequestDTO(name = "Nodir", age = 25)
            assertFalse(service.validateDTO(dto), "Expected false when name equals \"Nodir\"")
        }

        @Test
        fun `validateDTO returns false when age is not positive`() {
            val dto = MonadRequestDTO(name = "Alice", age = 0)
            assertFalse(service.validateDTO(dto), "Expected false when age is not positive")
        }

        @Test
        fun `validateDTO returns false when both name equals Nodir and age is not positive`() {
            val dto = MonadRequestDTO(name = "Nodir", age = -5)
            assertFalse(service.validateDTO(dto), "Expected false when name equals \"Nodir\" and age is not positive")
        }
    }
}