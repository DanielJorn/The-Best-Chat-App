package com.danjorn.models

import com.google.firebase.database.Exclude

data class MessageResponse(
        @Exclude
        override var id: String? = null,
        @Exclude
        var chatId: String? = null,
        @Exclude
        var authorId: String? = null,
        var messageText: String? = null,
        var timestamp: Long? = null
) : WithId() {
    fun toUIMessage(): Message {
        return Message(
                id = id,
                chatId = chatId,
                messageText = messageText.orEmpty(),
                authorUser = User(id = authorId),
                timestamp = timestamp!!
        )
    }
}

data class Message(
        override var id: String? = null,
        var chatId: String? = null,
        var messageText: String = "",
        var authorUser: User = User(),
        var timestamp: Long = 0
) : WithId()