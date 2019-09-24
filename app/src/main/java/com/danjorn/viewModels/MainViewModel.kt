package com.danjorn.viewModels

import android.app.Activity
import android.app.Application
import android.location.Location
import android.util.Log
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
import com.danjorn.models.ChatPojo
import com.danjorn.viewModels.liveData.FirebaseObserveLiveData
import com.firebase.ui.auth.AuthUI
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val tag = MainViewModel::class.java.simpleName
    private val rootChatRef = sChatsNode.toDatabaseReference()

    private val chatUpdateObserver = FirebaseObserveLiveData(rootChatRef)
    private val loadedChats = MutableLiveData<ChatPojo>()

    val chatsLiveData = MediatorLiveData<ChatPojo>().apply {
        addSource(chatUpdateObserver) { snapshot ->
            val chatPojo = snapshot.getValueAndId(ChatPojo::class.java)
            this.value = chatPojo
        }
        addSource(loadedChats) { chatPojo ->
            this.value = chatPojo
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

    fun userUpdatesChats() {
        viewModelScope.launch {
            val userLocation = suspendLocation(getApplication())
            Log.d(tag, "userUpdatesChats: user location is $userLocation")
            val availableKeys = chatKeysInRadius(MAX_RADIUS, userLocation)

            availableKeys.forEach { keyAndLocation ->
                val chatKey = keyAndLocation.first
                val chatLocation = Location("").apply {
                    latitude = keyAndLocation.second.latitude
                    longitude = keyAndLocation.second.longitude
                }

                val currentChatPojo = loadChatPojo(chatKey)
                Log.d(tag, "userUpdatesChats: current chat is $currentChatPojo")

                if (userInChatZone(userLocation, chatLocation, currentChatPojo.radius)) {
                    loadedChats.value = currentChatPojo
                    startObserveChat(keyAndLocation.first)
                }
            }
        }
    }

    fun String.toDatabaseReference(): DatabaseReference {
        val rootRef = FirebaseDatabase.getInstance().reference
        return rootRef.child(this)
    }

    private fun startObserveChat(chatId: String) {
        chatUpdateObserver.addUpdateListener(chatId)
    }

    private fun userInChatZone(userLocation: Location, chatLocation: Location, chatRadius: Int?): Boolean {
        val distanceToChat = userLocation.distanceTo(chatLocation) / 1000 //Convert to Km
        return distanceToChat < chatRadius!!
    }

    private suspend fun loadChatPojo(chatKey: String): ChatPojo {
        val toDownloadRef = rootChatRef.child(chatKey)
        return suspendDownload(toDownloadRef, ChatPojo::class.java)!!
    }
}