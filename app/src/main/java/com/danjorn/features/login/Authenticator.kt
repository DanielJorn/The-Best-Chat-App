package com.danjorn.features.login

import android.util.Log
import com.danjorn.core.authentication.UserEntity
import com.danjorn.core.exception.Failure
import com.danjorn.core.functional.Either
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Authenticator
@Inject constructor() {
    private var userLoggedIn = false

    private val fbLogin = DatabaseLogin.FirebaseLogin()

    fun userLoggedIn() = userLoggedIn

     suspend fun signIn(user: UserEntity): Either<Failure, UserEntity> {
        if (!userExists(user)) {
            return Either.Left(LoginFailure.UserNotExists)
        }
        if (!fbLogin.passwordCorrect(user)) {
            return Either.Left(LoginFailure.IncorrectPassword)
        }
        userLoggedIn = true
        return Either.Right(user)
    }

    private suspend fun userExists(user: UserEntity): Boolean {
        return fbLogin.userExists(user)
    }
}