package com.danjorn.firebase.auth

import android.app.Activity
import android.content.Intent
import androidx.core.app.ActivityCompat
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthManager : FirebaseAuthInterface {
    //TODO I think it'd be better if I had it injected rather than hard-coded
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun showLoginActivity(activityLaunchFrom: Activity, requestCode: Int) {
        val providers = getAuthProviders()

        ActivityCompat.startActivityForResult(
                activityLaunchFrom,
                configureAuthUI(AuthUI.getInstance(), providers),
                requestCode,
                null
        )
    }

    //TODO I have to inject it somehow...
    private fun configureAuthUI(instance: AuthUI, providers: MutableList<AuthUI.IdpConfig>): Intent {
        return instance
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
    }

    private fun getAuthProviders(): MutableList<AuthUI.IdpConfig> {
        return arrayListOf(
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.FacebookBuilder().build()
        )
    }
}