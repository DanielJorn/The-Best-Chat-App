package com.danjorn.database

import android.location.Location
import com.danjorn.configs.MAX_RADIUS
import com.danjorn.configs.sChatsNode
import com.danjorn.coroutines.chatsInRadius
import com.danjorn.coroutines.downloadFrom
import com.danjorn.ktx.toDatabaseRef
import com.danjorn.models.Chat
import com.danjorn.models.ChatResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FirebaseDatabaseManager : DatabaseManager {
    private val chatsReference = sChatsNode.toDatabaseRef()

    override fun getAvailableChats(userLocation: Location,
                                   onComplete: () -> Unit,
                                   onChatGot: (Chat) -> Unit,
                                   onError: (Throwable) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val chatsKeysAndLocations = chatsInRadius(MAX_RADIUS, userLocation)

            chatsKeysAndLocations.forEach { keyAndLocation ->
                val chatKey = keyAndLocation.first
                val chatLocation = keyAndLocation.second

                val chat = downloadFrom(chatsReference.child(chatKey), ChatResponse::class.java)
                if (userInChatZone(userLocation, chatLocation, chat!!.radius)) {
                    //onChatGot(chat) TODO how to map it to Ui version of Chat??
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