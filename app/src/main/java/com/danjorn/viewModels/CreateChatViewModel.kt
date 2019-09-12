package com.danjorn.viewModels

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danjorn.models.database.ChatPojo
import com.danjorn.utils.uploadChat

class CreateChatViewModel : ViewModel() {
    val liveData = MutableLiveData<String>()

    /*
    *
    *
    *
    *
    */

    fun uploadChat(activity: Activity, chatPojo: ChatPojo) {
        val successListener: (String) -> Unit = { liveData.postValue(it) }
        val failureListener: (Exception) -> Unit = {
            Toast.makeText(activity, "There is a $it exception :(", Toast.LENGTH_SHORT).show()
        }
        uploadChat(activity, chatPojo, successListener, failureListener)
    }


}