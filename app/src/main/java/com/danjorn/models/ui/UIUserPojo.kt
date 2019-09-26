package com.danjorn.models.ui

import com.danjorn.models.WithId

data class UIUserPojo(
        override var id: String? = null,
        var name: String = "",
        var avatarImageUrl: String = ""
) : WithId()
