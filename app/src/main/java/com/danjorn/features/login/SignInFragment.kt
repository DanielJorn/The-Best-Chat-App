package com.danjorn.features.login

import android.os.Bundle
import android.view.View
import com.danjorn.core.authentication.UserEntity
import com.danjorn.core.platform.BaseFragment
import com.danjorn.views.R
import kotlinx.android.synthetic.main.fragment_login.*

class SignInFragment : BaseFragment() {

    private lateinit var viewModel: SignInViewModel

    override fun layoutId(): Int {
        return R.layout.fragment_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        viewModel = viewModelFactory.create(SignInViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_sign_in.setOnClickListener { signInUser() }
        tv_sign_up_link.setOnClickListener { launchSignUp() }
    }

    private fun signInUser() {
        val user = createUserFromFields()
        viewModel.signIn(user)
    }

    private fun createUserFromFields(): UserEntity {
        val name = et_login_username.text.toString()
        val password = et_login_password.text.toString()
        return UserEntity(name, password)
    }

    private fun launchSignUp() {
        val intent = SignUpActivity.callingIntent(requireContext())
        startActivity(intent)
    }
}