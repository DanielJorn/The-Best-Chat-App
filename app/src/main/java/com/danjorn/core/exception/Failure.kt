package com.danjorn.core.exception

/**
 * Base class for handling errors/failures/exceptions.
 * Evert feature specific failure should extend [FeatureFailure].
 */
sealed class Failure {
    object NetworkFailure : Failure()
    object DatabaseError: Failure()
    object ServerError : Failure()

    abstract class FeatureFailure : Failure()
}