package com.danjorn.database

import android.location.Location
import com.danjorn.models.Chat

interface DatabaseManager {
    fun getAvailableChats(userLocation: Location,
                          onComplete: () -> Unit,
                          onChatGot: (Chat) -> Unit,
                          onError: (Throwable) -> Unit)
}