package com.danjorn.models.db

import com.danjorn.models.WithId
import com.google.firebase.database.Exclude

data class MessagePojo(
        @Exclude
        override var id: String?,
        @Exclude
        var chatId: String? = null,
        @Exclude
        var authorId: String? = null,
        var messageText: String? = null,
        var timestamp: Long? = null
) : WithId()