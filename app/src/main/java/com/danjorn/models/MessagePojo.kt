package com.danjorn.models

data class MessagePojo(
        var chatId:      String? = null,
        var messageId:   String? = null,
        var authorId:    String? = null,
        var messageText: String? = null,
        var timestamp:   Long? = null
)