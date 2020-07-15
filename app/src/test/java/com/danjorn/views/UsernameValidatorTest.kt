package com.danjorn.views

import com.danjorn.features.signup.*
import org.hamcrest.CoreMatchers.*

import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class UsernameValidatorTest {

    private val validator = UsernameValidator()

    private fun validate(name: String): ValidationResult? {
        return validator.isValid(name)
    }

    @Test
    fun whenNameBlank_returnNameBlankError() {
        val blankName = ""
        val result = validate(blankName)
        assertThat(result, instanceOf(NameCantBeBlankError::class.java))
    }

    @Test
    fun whenNameNotBlank_notReturnNameBlankError() {
        val notBlankName = "1"
        val result = validate(notBlankName)
        assertThat(result, not(instanceOf(NameCantBeBlankError::class.java)))
    }

    @Test
    fun whenNameHasLessThanThreeCharacters_returnMustHaveThreeCharactersOrMoreError() {
        val smallName = "12"
        val result = validate(smallName)
        assertThat(result, `is`(instanceOf(NameMustHaveThreeCharactersOrMoreError::class.java)))
    }

    @Test
    fun whenNameHasThreeCharactersOrMore_notReturnMustHaveThreeCharactersOrMoreError() {
        val threeCharsName = "123"
        val result = validate(threeCharsName)
        assertThat(result, not(instanceOf(NameMustHaveThreeCharactersOrMoreError::class.java)))
    }

    @Test
    fun whenNameHasMoreThanTwentyCharacters_returnMustHaveTwentyCharactersOrLessError() {
        val twentyOneCharName = "123456789012345678901"
        val result = validate(twentyOneCharName)
        assertThat(result, `is`(instanceOf(NameMustHaveTwentyCharactersOrLessError::class.java)))
    }

    @Test
    fun whenNameHasTwentyCharactersOrLess_notReturnNameMustHaveTwentyCharactersOrLessError() {
        val twentyCharsName = "12345678901234567890"
        val result = validate(twentyCharsName)
        assertThat(result, not(instanceOf(NameMustHaveTwentyCharactersOrLessError::class.java)))
    }

    @Test
    fun whenNameHasSpaces_returnNameMustNotHaveSpacesError() {
        val nameWithSpaces = "123 456"
        val result = validate(nameWithSpaces)
        assertThat(result, `is`(instanceOf(NameMustNotHaveSpacesError::class.java)))
    }

    @Test
    fun whenNameHasNoSpaces_notReturnNameMustNotHaveSpacesError() {
        val nameWithoutSpaces = "123456"
        val result = validate(nameWithoutSpaces)
        assertThat(result, not(instanceOf(NameMustNotHaveSpacesError::class.java)))
    }

    @Test
    fun whenNameValid_notReturnError(){
        val validName = "validName"
        val result = validate(validName)
        assertThat(result, not(instanceOf(ValidationFailure::class.java)))
    }

    @Test
    fun whenNameValid_returnValidationSuccess(){
        val validName = "validName"
        val result = validate(validName)
        assertThat(result, `is`(instanceOf(Success::class.java)))
    }
}
