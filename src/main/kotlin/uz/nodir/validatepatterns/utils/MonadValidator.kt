package uz.nodir.validatepatterns.utils

import java.util.*
import kotlin.reflect.KFunction0


/**
@author: Nodir
@date: 05.07.2025
@group: Meloman

 **/


class MonadValidator<T>(
    private val obj: T
) {


    companion object {
        fun <T> of(t: T): MonadValidator<T> {
            return MonadValidator(Objects.requireNonNull(t))
        }
    }

    fun validate(condition: (T) -> Boolean, message: String): MonadValidator<T> =
        apply {
            if (!condition(obj)) {
                throw IllegalArgumentException(message)
            }
        }

    fun <U> validate(
        projection: (T) -> U,
        validation: (U) -> Boolean,
        message: String
    ): MonadValidator<T> = apply {
        validate({ t: T -> validation(projection(t)) }, message)
    }

}