package com.danjorn.viewModels

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danjorn.models.ChatPojo
import com.danjorn.utils.suspendUploadChat
import kotlinx.coroutines.launch

class CreateChatViewModel : ViewModel() {
    val liveData = MutableLiveData<String>()
    private val tag = CreateChatViewModel::class.java.simpleName

    fun uploadChat(activity: Activity, chatPojo: ChatPojo) {
        viewModelScope.launch {
            suspendUploadChat(activity, chatPojo)
        }
    }


}