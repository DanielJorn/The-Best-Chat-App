package com.danjorn.features.login

import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.danjorn.core.authentication.UserEntity
import com.danjorn.core.platform.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel
@Inject constructor() : BaseViewModel() {

    private val signInSuccess = MutableLiveData<Unit>()

    private val fbLogin = DatabaseLogin.FirebaseLogin()

    fun signIn(user: UserEntity) {
        viewModelScope.launch {
            if (!userExists(user)) {
                failure.value = LoginFailure.UserNotExists
                Log.d("TAG", "signIn: UserNotExists")
                return@launch
            }
            if (!fbLogin.passwordCorrect(user)){
                failure.value = LoginFailure.IncorrectPassword
                Log.d("TAG", "signIn: IncorrectPassword")
                return@launch
            }
            signInSuccess.value = signInSuccess.value
            Log.d("TAG", "signIn: success")
        }
    }

    fun signUp(user: UserEntity) {
        viewModelScope.launch {
            fbLogin.signUp(user)
            Log.d("TAG", "signUp: the user is created")
        }
    }

    private suspend fun userExists(user: UserEntity): Boolean {
        return fbLogin.userExists(user)
    }
}