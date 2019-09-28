package com.danjorn.models

import com.google.firebase.database.Exclude

data class ChatResponse(
        @Exclude override var id: String? = null,
        var title: String? = null,
        var chatImageUrl: String? = null,
        var radius: Int? = null
) : WithId() {
    @Exclude
    fun deepCopyFrom(chatResponse: ChatResponse) {
        this.id = chatResponse.id
        this.title = chatResponse.title
        this.chatImageUrl = chatResponse.chatImageUrl
        this.radius = chatResponse.radius
    }
}

data class Chat(
        override var id: String?,
        var chatTitle: String = "",
        var chatImageUrl: String = "",
        var radius: Int = 0,
        var lastMessage: Message = Message()
) : WithId()