package com.danjorn.models.database

import com.google.firebase.database.Exclude

class ChatPojo {
    @Exclude
    var id: String? = null

    var title: String? = null
    var chatImageUrl: String? = null
    var lastMessageId: String? = null
    var radius: Int? = null

    constructor(id: String?, title: String?, chatImageUrl: String?, lastMessageId: String?, radius: Int?) {
        this.id = id
        this.title = title
        this.chatImageUrl = chatImageUrl
        this.lastMessageId = lastMessageId
        this.radius = radius
    }

    constructor(title: String?, chatImageUrl: String?, lastMessageId: String?, radius: Int?) : this(null, title, chatImageUrl, lastMessageId, radius)

    constructor(title: String?, chatImageUrl: String?, radius: Int?) : this(title, chatImageUrl, null, radius)



    constructor()
}