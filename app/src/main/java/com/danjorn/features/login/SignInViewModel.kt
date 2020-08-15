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

    private val liveData = MutableLiveData<Boolean>()

    private val fbLogin = DatabaseLogin.FirebaseLogin()

    fun userExists(user: UserEntity) {
        viewModelScope.launch {
            Log.d("TAG", "onViewCreated: user exists: ${fbLogin.userExists(user)}")
        }
    }

    fun signUp(user: UserEntity){
        viewModelScope.launch {
            fbLogin.signUp(user)
            Log.d("TAG", "signUp: the user is created")
        }
    }

    fun signIn(user: UserEntity) {
        viewModelScope.launch {
            fbLogin.signIn(user)
        }
    }
}