package com.danjorn.database

import android.location.Location
import com.danjorn.models.ChatResponse

interface DatabaseManager {
    fun getAvailableChats(userLocation: Location,
                          onComplete: () -> Unit,
                          onChatGot: (ChatResponse) -> Unit, //TODO Change to plain UI Chat pojo!!!!!!
                          onError: (Throwable) -> Unit)
}