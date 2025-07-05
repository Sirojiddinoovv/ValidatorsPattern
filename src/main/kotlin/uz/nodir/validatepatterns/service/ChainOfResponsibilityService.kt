package uz.nodir.validatepatterns.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import uz.nodir.validatepatterns.model.dto.ChainRequestDTO
import uz.nodir.validatepatterns.utils.ChainOfResponsibilityValidator


/**
@author: Nodir
@date: 05.07.2025
@group: Meloman

 **/

@Service
class ChainOfResponsibilityService {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    fun validateUser(user: ChainRequestDTO): Boolean {
        log.info("Received command to validating user with requestDTO: {}", user)

        return try {
            ChainOfResponsibilityValidator()
                .next(user.username, String::isBlank, "Username must not be blank")
                .next({ !user.email.contains("@") }, "Email must contain '@'")
                .next({ user.age !in 18..99 }, "Age must be between 18 and 99")
                .onError { msg -> log.warn("Validation failed for user {}: {}", user, msg) }
                .throwIfError()
            log.info("Finished command to validate user with success result")
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}