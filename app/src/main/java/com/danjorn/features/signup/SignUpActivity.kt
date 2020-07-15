package com.danjorn.features.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.danjorn.core.platform.BaseActivity
import com.danjorn.core.platform.BaseFragment
import com.danjorn.views.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun fragment(): BaseFragment {
        return SignUpFragment()
    }
}