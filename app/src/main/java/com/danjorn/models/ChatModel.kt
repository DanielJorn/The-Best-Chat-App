package com.danjorn.models

import com.google.firebase.database.Exclude

data class ChatResponse(
        @Exclude override var id: String? = null,
        var title: String? = null,
        var chatImageUrl: String? = null,
        var radius: Int? = null
) : WithId() {
    @Exclude
    fun toUIChat() = UIChat(
            id = id,
            chatTitle = title!!,
            chatImageUrl = chatImageUrl.orEmpty(),
            radius = radius!!
    )
}

data class UIChat(
        override var id: String?,
        var chatTitle: String = "",
        var chatImageUrl: String = "",
        var radius: Int = 0,
        var lastMessage: UIMessage = UIMessage()
) : WithId() {
    fun deepCopyFrom(UIChat: UIChat) {
        this.id = UIChat.id
        this.chatTitle = UIChat.chatTitle
        this.chatImageUrl = UIChat.chatImageUrl
        this.radius = UIChat.radius
    }
}