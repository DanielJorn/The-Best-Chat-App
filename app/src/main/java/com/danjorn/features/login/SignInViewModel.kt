package com.danjorn.features.login

import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.danjorn.core.authentication.UserEntity
import com.danjorn.core.exception.Failure
import com.danjorn.core.navigation.Navigator
import com.danjorn.core.platform.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel
@Inject constructor() : BaseViewModel() {

    private val fbLogin = DatabaseLogin.FirebaseLogin()

    @Inject
    lateinit var authenticator: Authenticator

    val signInSuccess = MutableLiveData<UserEntity>()

    fun signIn(user: UserEntity) {
        viewModelScope.launch {
            Log.d("TAG", "signIn: before auth.signIn")
            authenticator.signIn(user).either(::handleFailure, ::handleSuccess)
            Log.d("TAG", "signIn: after auth.signIn")
        }
    }

    private fun handleSuccess(user: UserEntity) {
        signInSuccess.postValue(user)
    }

    fun signUp(user: UserEntity) {
        viewModelScope.launch {
            fbLogin.signUp(user)
            Log.d("TAG", "signUp: the user is created")
        }
    }
}