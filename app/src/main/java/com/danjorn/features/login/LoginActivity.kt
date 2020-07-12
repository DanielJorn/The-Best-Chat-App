package com.danjorn.features.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity(){
    companion object {
        fun callingIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}