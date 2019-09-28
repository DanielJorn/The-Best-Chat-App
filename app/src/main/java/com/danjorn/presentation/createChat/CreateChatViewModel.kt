package com.danjorn.presentation.createChat

import android.app.Activity
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danjorn.models.ChatResponse
import com.danjorn.utils.suspendUploadChat
import kotlinx.coroutines.launch

class CreateChatViewModel : ViewModel() {
    val onChatCreatedLiveData = MutableLiveData<String>()
    private val tag = CreateChatViewModel::class.java.simpleName

    fun uploadChat(activity: Activity, chatResponse: ChatResponse, chatImageUri: Uri?) {
        viewModelScope.launch {
            suspendUploadChat(activity, chatResponse, chatImageUri) {
                onChatCreatedLiveData.value = it
            }
        }
    }
}