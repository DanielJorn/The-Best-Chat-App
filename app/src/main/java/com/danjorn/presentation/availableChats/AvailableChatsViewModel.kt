package com.danjorn.presentation.availableChats

import android.app.Activity
import android.app.Application
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.danjorn.configs.MAX_RADIUS
import com.danjorn.configs.sChatsNode
import com.danjorn.coroutines.chatKeysInRadius
import com.danjorn.coroutines.suspendDownload
import com.danjorn.coroutines.suspendLocation
import com.danjorn.ktx.getValueAndId
import com.danjorn.ktx.toDatabaseRef
import com.danjorn.models.ChatResponse
import com.danjorn.presentation.availableChats.liveData.FirebaseObserveLiveData
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.launch

class AvailableChatsViewModel(application: Application) : AndroidViewModel(application) {

    private val tag = AvailableChatsViewModel::class.java.simpleName
    private val rootChatRef = sChatsNode.toDatabaseRef()

    private val chatUpdateObserver = FirebaseObserveLiveData(rootChatRef)
    private val loadedChats = MutableLiveData<ChatResponse>()

    val chatsLiveData = MediatorLiveData<ChatResponse>().apply {
        addSource(chatUpdateObserver) { snapshot ->
            val chatPojo = snapshot.getValueAndId(ChatResponse::class.java)
            this.value = chatPojo
        }
        addSource(loadedChats) { chatPojo ->
            this.value = chatPojo
        }
    }

    fun userRefreshChats(onComplete: () -> Unit) {
        viewModelScope.launch {
            val userLocation = suspendLocation(getApplication())
            val availableKeys = chatKeysInRadius(MAX_RADIUS, userLocation)

            availableKeys.forEach { keyAndLocation ->
                val chatKey = keyAndLocation.first
                val chatLocation = Location("").apply {
                    latitude = keyAndLocation.second.latitude
                    longitude = keyAndLocation.second.longitude
                }

                val currentChatPojo = loadChatPojo(chatKey)

                if (userInChatZone(userLocation, chatLocation, currentChatPojo.radius)) {
                    loadedChats.value = currentChatPojo
                    startObserveChat(keyAndLocation.first)
                }
            }
            onComplete()
        }
    }

    fun showLoginActivity(activity: Activity, requestCode: Int) {

        val providers = arrayListOf(
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.FacebookBuilder().build()
        )

        ActivityCompat.startActivityForResult(
                activity,
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                requestCode,
                null
        )
    }

    private fun startObserveChat(chatId: String) {
        chatUpdateObserver.addUpdateListener(chatId)
    }

    private fun userInChatZone(userLocation: Location, chatLocation: Location, chatRadius: Int?): Boolean {
        val distanceToChat = userLocation.distanceTo(chatLocation) / 1000 //Convert to Km
        return distanceToChat < chatRadius!!
    }

    private suspend fun loadChatPojo(chatKey: String): ChatResponse {
        val toDownloadRef = rootChatRef.child(chatKey)
        return suspendDownload(toDownloadRef, ChatResponse::class.java)!!
    }
}