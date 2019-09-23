package com.danjorn.models

import com.google.firebase.database.Exclude

data class ChatPojo(
        @Exclude override var id: String? = null,
        var title: String? = null,
        var chatImageUrl: String? = null,
        var radius: Int? = null
) : WithId() {

    @Exclude
    fun deepCopyFrom(chatPojo: ChatPojo) {
        this.id = chatPojo.id
        this.title = chatPojo.title
        this.chatImageUrl = chatPojo.chatImageUrl
        this.radius = chatPojo.radius
    }
}