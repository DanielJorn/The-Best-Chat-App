package com.danjorn.ktx

import com.danjorn.models.WithId
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

fun <T : WithId> DataSnapshot.getValueAndId(clazz: Class<T>): T? {
    val value = getValue(clazz)
    value?.id = key
    return value
}

fun String.toDatabaseRef(): DatabaseReference {
    val rootRef = FirebaseDatabase.getInstance().reference
    return rootRef.child(this)
}

fun String.toFirebaseStorageRef(): StorageReference {
    val rootRef = FirebaseStorage.getInstance().reference
    return rootRef.child(this)
}
