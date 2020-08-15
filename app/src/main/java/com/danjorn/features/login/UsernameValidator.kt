package com.danjorn.features.login

class UsernameValidator : Validator<String> {

    override fun isValid(field: String): ValidationResult {
        return when {
            field.isBlank() -> NameCantBeBlankError
            nameNotLongEnough(field) -> NameMustHaveThreeCharactersOrMoreError
            nameTooLong(field) -> NameMustHaveTwentyCharactersOrLessError
            nameHasSpaces(field) -> NameMustNotHaveSpacesError
            else -> Success
        }
    }

    private fun nameNotLongEnough(name: String): Boolean {
        return name.length < 3
    }

    private fun nameTooLong(name: String): Boolean {
        return name.length > 20
    }

    private fun nameHasSpaces(name: String): Boolean {
        return name.contains(' ')
    }

}