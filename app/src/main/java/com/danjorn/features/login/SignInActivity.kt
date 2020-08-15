package com.danjorn.features.login

import android.content.Context
import android.content.Intent
import com.danjorn.core.platform.BaseActivity
import com.danjorn.core.platform.BaseFragment

class SignInActivity : BaseActivity(){
    companion object {
        fun callingIntent(context: Context) = Intent(context, SignInActivity::class.java)
    }

    override fun fragment(): BaseFragment = SignInFragment()

}