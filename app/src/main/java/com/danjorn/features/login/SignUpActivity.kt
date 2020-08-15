package com.danjorn.features.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.danjorn.core.platform.BaseActivity
import com.danjorn.core.platform.BaseFragment

class SignUpActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, SignUpActivity::class.java)
    }

    override fun fragment(): BaseFragment {
        return SignUpFragment()
    }
}