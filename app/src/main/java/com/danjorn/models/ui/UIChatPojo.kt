package com.danjorn.models.ui

import com.danjorn.models.WithId

data class UIChatPojo(
        override var id: String?,
        var chatTitle: String = "",
        var chatImageUrl: String = "",
        var radius: Int = 0,
        var lastMessage: UIMessagePojo = UIMessagePojo()
) : WithId()