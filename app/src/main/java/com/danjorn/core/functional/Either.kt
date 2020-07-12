package com.danjorn.core.functional

/**
 * Represents a result of some operation.
 * Instance of this class can only be "failure" or "success".
 * By convention, failure is [Left]
 * and success is [Right].
 */
sealed class Either<out L, out R> {
    data class Left<out L>(val a: L) : Either<L, Nothing>()
    data class Right<out R>(val b: R) : Either<Nothing, R>()

    fun either(fnL: (L) -> Any, fnR: (R) -> Any): Any =
            when (this) {
                is Left -> fnL(a)
                is Right -> fnR(b)
            }
}
