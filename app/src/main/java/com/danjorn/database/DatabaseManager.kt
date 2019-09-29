package com.danjorn.database

import android.location.Location
import com.danjorn.models.UIChat

interface DatabaseManager {
    fun getAvailableChats(userLocation: Location,
                          onComplete: () -> Unit,
                          onChatGot: (UIChat) -> Unit, //
                          onError: (Throwable) -> Unit)
}