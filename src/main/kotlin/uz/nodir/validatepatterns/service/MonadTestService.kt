package uz.nodir.validatepatterns.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import uz.nodir.validatepatterns.model.dto.MonadRequestDTO
import uz.nodir.validatepatterns.utils.MonadValidator


/**
@author: Nodir
@date: 05.07.2025
@group: Meloman

 **/

@Service
class MonadTestService {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
        private const val WORD = "HELLO WORLD!"
    }


    fun validateStr(text: String): Boolean {
        log.info("Received command to validate by monad validator with text: $text")

        return try {
            MonadValidator.of(text)
                .validate(String::isNotEmpty, "String is empty")
                .validate({ text.length > 5 }, "Length must be more than 5 chars")
                .validate({ text == WORD }, "Incorrect text")

            log.info("Finished command to validate by monad validator with success result")
            true
        } catch (e: IllegalArgumentException) {
            log.warn("Validation failed: {}", e.message)
            false
        }
    }

    fun validateDTO(requestDTO: MonadRequestDTO): Boolean {
        log.info("Received command to validate by monad validator with requestDTO: $requestDTO")

        return try {
            MonadValidator.of(requestDTO)
                .validate(MonadRequestDTO::name, { "Nodir" != it }, "Name must be \"Nodir\"")
                .validate(MonadRequestDTO::age, { it > 0 }, "Age must be positive")
            true
        } catch (e: IllegalArgumentException) {
            log.warn("Validation failed: {}", e.message)
            false
        }
    }

}