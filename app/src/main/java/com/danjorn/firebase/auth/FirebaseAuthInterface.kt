package com.danjorn.firebase.auth

import android.app.Activity

interface FirebaseAuthInterface {
    fun isUserLoggedIn(): Boolean
    fun showLoginActivity(activityLaunchFrom: Activity, requestCode: Int)
}