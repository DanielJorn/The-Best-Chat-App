package com.danjorn.features.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.danjorn.core.authentication.UserEntity
import com.danjorn.core.exception.Failure
import com.danjorn.core.platform.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel
@Inject constructor(private val databaseLogin: DatabaseLogin.FirebaseLogin) : BaseViewModel() {

    var liveData : MutableLiveData<Unit> = MutableLiveData()

    fun signUp(user: UserEntity){
        viewModelScope.launch {
            if (databaseLogin.userExists(user)){
                failure.value = UserExistsFailure()
            } else liveData.value = databaseLogin.signUp(user)
        }
    }
}

class UserExistsFailure : Failure.FeatureFailure()
