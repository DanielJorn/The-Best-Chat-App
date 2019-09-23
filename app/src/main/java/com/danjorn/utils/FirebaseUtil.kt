package com.danjorn.utils

import android.app.Activity
import android.location.Location
import com.danjorn.configs.sChatLocationNode
import com.danjorn.configs.sChatsNode
import com.danjorn.coroutines.suspendLocation
import com.danjorn.models.ChatPojo
import com.firebase.geofire.GeoLocation
import com.firebase.geofire.core.GeoHash
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val tag = "FirebaseUtils"

suspend fun suspendUploadChat(activity: Activity, chatPojo: ChatPojo) {
    val location = suspendLocation(activity.application)

    //room for uploading image and getting url


    val chatsRef = FirebaseDatabase.getInstance().reference.child(sChatsNode)
    val chatId = chatsRef.push().key

    val updateMap = getUpdateMap(location, chatPojo, chatId!!)
    suspendCreateChat(updateMap)


}

private fun getUpdateMap(location: Location, chatPojo: ChatPojo, chatId: String): Map<String, Any> { //TODO updateMap?! Silly name I have to create some another. And has ugly signature
    val geoLocation = GeoLocation(location.latitude, location.longitude)
    val geoHash = GeoHash(geoLocation)
    val map = HashMap<String, Any>()

    map["/$sChatsNode/$chatId"] = chatPojo
    map["/$sChatLocationNode/$chatId/g"] = geoHash.geoHashString
    map["/$sChatLocationNode/$chatId/l"] = listOf(location.latitude, location.longitude)

    return map
}

/**
 * Function is required for creating chat in Firebase database
 * @param updateMap map with values you want to update
 * */

private suspend fun suspendCreateChat(updateMap: Map<String, Any>) {
    suspendCoroutine<String> { cont ->
        FirebaseDatabase.getInstance().reference.updateChildren(updateMap)
                .addOnFailureListener { cont.resumeWithException(it) }
    }
}
