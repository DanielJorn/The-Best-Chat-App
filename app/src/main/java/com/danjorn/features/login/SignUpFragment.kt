package com.danjorn.features.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.danjorn.core.authentication.UserEntity
import com.danjorn.core.exception.Failure
import com.danjorn.core.platform.BaseFragment
import com.danjorn.views.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.fragment_login.*

class SignUpFragment : BaseFragment() {

    private val TAG = "SignUpFragment"

    private lateinit var viewModel : SignUpViewModel

    override fun layoutId(): Int {
        return R.layout.activity_sign_up
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        viewModel = viewModelFactory.create(SignUpViewModel::class.java)
        viewModel.liveData.observe(this, Observer { handleSuccess(it) })
        viewModel.failure.observe(this, Observer { handleFailure(it) })
    }

    private fun handleFailure(failure: Failure?) {
        Log.d(TAG, "handleFailure: failure is $failure")
    }

    private fun handleSuccess(userEntity: Unit?) {
        Log.d(TAG, "handleSuccess: userEntity is $userEntity")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_sign_up.setOnClickListener { viewModel.signUp(getUser()) }
    }

    private fun getUser(): UserEntity {
        return UserEntity(et_signup_username.text.toString(), "")//todo add password
    }
}