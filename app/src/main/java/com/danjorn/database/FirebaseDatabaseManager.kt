package com.danjorn.database

import android.location.Location
import com.danjorn.configs.MAX_RADIUS
import com.danjorn.coroutines.chatsInRadius
import com.danjorn.coroutines.downloadFrom
import com.danjorn.ktx.toDatabaseRef
import com.danjorn.models.ChatResponse
import com.danjorn.models.UIChat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FirebaseDatabaseManager : DatabaseManager {
    private val chatsReference = CHATS.toDatabaseRef()
    private val tag = FirebaseDatabaseManager::class.java.simpleName

    override fun getAvailableChats(userLocation: Location,
                                   onComplete: () -> Unit,
                                   onChatGot: (UIChat) -> Unit,
                                   onError: (Throwable) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val chatsKeysAndLocations = chatsInRadius(MAX_RADIUS, userLocation)

            chatsKeysAndLocations.forEach { keyAndLocation ->
                val chatKey = keyAndLocation.first
                val chatLocation = keyAndLocation.second

                val chat = downloadFrom(chatsReference.child(chatKey), ChatResponse::class.java)
                if (userInChatZone(userLocation, chatLocation, chat!!.radius)) {
                    onChatGot(chat.toUIChat())
                }
            }
            onComplete()
        }
    }

    private fun userInChatZone(userLocation: Location, chatLocation: Location, chatRadius: Int?): Boolean {
        val distanceToChat = userLocation.distanceTo(chatLocation) / 1000 //Convert to Km
        return distanceToChat < chatRadius!!
    }
}