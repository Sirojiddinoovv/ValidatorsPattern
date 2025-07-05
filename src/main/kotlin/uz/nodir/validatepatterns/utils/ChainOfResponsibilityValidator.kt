package uz.nodir.validatepatterns.utils

import org.slf4j.LoggerFactory


/**
@author: Nodir
@date: 05.07.2025
@group: Meloman

 **/
class ChainOfResponsibilityValidator {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    private var hasError = false
    private var message: String? = null


    fun next(
        condition: () -> Boolean,
        message: String
    ): ChainOfResponsibilityValidator = apply {
        log.info("Validator chain with condition")
        if (!hasError && condition()) {
            hasError = true
            this.message = message
        }
        log.info("Validator result: hasError=$hasError, message=$message")
    }

    /**
     * Adds an object-based validation to the chain.
     * Uses receiver-style validator for conciseness.
     */
    fun <T> next(
        obj: T,
        validator: T.() -> Boolean,
        message: String
    ): ChainOfResponsibilityValidator = apply {
        log.info("Validator chain with object: {}", obj)
        if (!hasError && obj.validator()) {
            hasError = true
            this.message = message
        }
        log.info("Validator result: hasError=$hasError, message=$message")
    }

    /**
     * Executes a custom action if an error has occurred.
     */
    fun onError(
        action: (message: String?) -> Unit
    ): ChainOfResponsibilityValidator = apply {
        if (hasError) {
            log.info("Executing onError action with message: {}", message)
            action(message)
        }
    }

    /**
     * Throws IllegalArgumentException if any validation failed.
     */
    fun throwIfError() {
        if (hasError) {
            log.error("Validator chain has error and will be thrown with message: {}", message)
            throw IllegalArgumentException(message)
        }
    }

    /** Returns true if any validation in the chain failed. */
    fun hasError(): Boolean = hasError

    /** Returns the error message if any. */
    fun errorMessage(): String? = message

}