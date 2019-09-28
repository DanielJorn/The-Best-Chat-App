package com.danjorn.models

import com.google.firebase.database.Exclude

data class User(
        override var id: String? = null,
        var name: String = "",
        var avatarImageUrl: String = ""
) : WithId()

data class UserResponse(
        @Exclude
        override var id: String? = null,
        var name: String = "",
        var avatarImageUrl: String = ""
) : WithId()