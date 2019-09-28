package com.danjorn.ktx

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.danjorn.models.WithId
import com.danjorn.ui.chatRoom.ChatRoomActivity
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

fun Activity.openChat(chatId: String) {
    val intent = Intent(this, ChatRoomActivity::class.java)
    val bundle = Bundle()
    bundle.putString(ChatRoomActivity.BUNDLE_CHAT_ID, chatId)
    startActivity(intent, bundle)
}
