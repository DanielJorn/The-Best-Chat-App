package com.danjorn.models.ui

import com.danjorn.models.WithId

data class UIMessagePojo(
        override var id: String? = null,
        var chatId: String? = null,
        var messageText: String = "",
        var authorUserPojo: UIUserPojo = UIUserPojo(),
        var timestamp: Long = 0
) : WithId()