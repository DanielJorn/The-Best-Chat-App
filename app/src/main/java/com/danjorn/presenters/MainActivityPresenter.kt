package com.danjorn.presenters

import android.app.Activity
import androidx.core.app.ActivityCompat.startActivityForResult
import com.firebase.ui.auth.AuthUI

class MainActivityPresenter {


    fun showLoginActivity(activity: Activity, requestCode: Int) {

        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build()
        )

        // Create and launch sign-in intent
        startActivityForResult(
            activity,
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            requestCode,
            null
        )
    }


}