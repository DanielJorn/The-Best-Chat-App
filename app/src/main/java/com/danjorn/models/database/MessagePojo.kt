package com.danjorn.models.database

import com.danjorn.models.ui.UIMessagePojo
import com.danjorn.views.custom.message.BaseMessageView

data class MessagePojo(
        var chatId:      String? = null,
        var messageId:   String? = null,
        var authorId:    String? = null,
        var messageText: String? = null,
        var timestamp:   Long? = null
)