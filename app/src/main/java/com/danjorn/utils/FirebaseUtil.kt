package com.danjorn.utils

import android.app.Activity
import android.location.Location
import android.net.Uri
import com.danjorn.configs.sChatsImages
import com.danjorn.coroutines.suspendLocation
import com.danjorn.database.sChatLocationNode
import com.danjorn.database.sChatsNode
import com.danjorn.ktx.toDatabaseRef
import com.danjorn.models.ChatResponse
import com.firebase.geofire.GeoLocation
import com.firebase.geofire.core.GeoHash
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val tag = "FirebaseUtils"

suspend fun suspendUploadChat(activity: Activity, chatResponse: ChatResponse, chatImageUri: Uri?,
                              onComplete: (String) -> Unit) {
    val location = suspendLocation(activity.application)

    val chatId = generateId()

    if (chatImageUri != null) {
        val uploadImagePath = getUploadImagePath(chatId)
        uploadFile(uploadImagePath, chatImageUri)
        chatResponse.chatImageUrl = getDownloadURL(uploadImagePath)
    }

    val updateMap = getUpdateMap(location, chatResponse, chatId)
    suspendCreateChat(updateMap)

    onComplete(chatId)
}

fun getUploadImagePath(chatId: String): String = "$sChatsImages/$chatId/chat_photo"

private fun getUpdateMap(location: Location, chatResponse: ChatResponse, chatId: String): Map<String, Any> { //TODO updateMap?! Silly name I have to create some another. And has ugly signature
    val geoLocation = GeoLocation(location.latitude, location.longitude)
    val geoHash = GeoHash(geoLocation)
    val map = HashMap<String, Any>()

    map["/$sChatsNode/$chatId"] = chatResponse
    map["/$sChatLocationNode/$chatId/g"] = geoHash.geoHashString
    map["/$sChatLocationNode/$chatId/l"] = listOf(location.latitude, location.longitude)

    return map
}

/**
 * Function is required for creating chat in Firebase database
 * @param updateMap map with values you want to update
 * */

private suspend fun suspendCreateChat(updateMap: Map<String, Any>) {
    suspendCoroutine<Unit?> { cont ->
        FirebaseDatabase.getInstance().reference.updateChildren(updateMap)
                .addOnSuccessListener { cont.resume(null) }
                .addOnFailureListener { cont.resumeWithException(it) }
    }
}

private fun generateId(): String {
    return "".toDatabaseRef().push().key!!
}