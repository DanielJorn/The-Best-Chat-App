package com.danjorn.features.login

interface Validator<T> {
    fun isValid(field: T): ValidationResult
}

sealed class ValidationResult

object Success : ValidationResult()

sealed class ValidationFailure: ValidationResult()

object NameCantBeBlankError : ValidationFailure()
object NameMustHaveThreeCharactersOrMoreError : ValidationFailure()
object NameMustHaveTwentyCharactersOrLessError : ValidationFailure()
object NameMustNotHaveSpacesError : ValidationFailure()




