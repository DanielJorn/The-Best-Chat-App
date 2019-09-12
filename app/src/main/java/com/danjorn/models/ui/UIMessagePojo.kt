package com.danjorn.models.ui

import com.danjorn.views.custom.message.BaseMessageView

data class UIMessagePojo(
        var messageId: String? = null,
        var authorId: String? = null,
        var messageText: String? = null,
        var timestamp: Long? = null,
        var viewType: Class<BaseMessageView>? = null
)