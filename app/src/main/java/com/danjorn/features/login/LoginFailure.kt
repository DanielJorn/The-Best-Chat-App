package com.danjorn.features.login

import com.danjorn.core.exception.Failure

class LoginFailure {
    object IncorrectPassword : Failure.FeatureFailure()
    object UserNotExists : Failure.FeatureFailure()
}