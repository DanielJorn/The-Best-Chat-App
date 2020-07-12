package com.danjorn.core.permission

import com.danjorn.core.exception.Failure

sealed class PermissionFailure : Failure.FeatureFailure(){
    object Denied : PermissionFailure()
    object PermanentlyDenied : PermissionFailure()
}
