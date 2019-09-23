package com.danjorn.ktx

import com.danjorn.models.WithId
import com.google.firebase.database.DataSnapshot


fun <T : WithId> DataSnapshot.getValueAndId(clazz: Class<T>): T? {
    val value = getValue(clazz)
    value?.id = key
    return value
}
