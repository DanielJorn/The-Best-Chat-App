package com.danjorn.coroutines

import android.location.Location
import com.danjorn.configs.sChatLocationNode
import com.danjorn.ktx.toDatabaseRef
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.firebase.geofire.GeoQuery
import com.firebase.geofire.GeoQueryEventListener
import com.google.firebase.database.DatabaseError
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private suspend fun suspendGeoQuery(geoQuery: GeoQuery): ArrayList<Pair<String, GeoLocation>> =
        suspendCoroutine {
            val keysList = ArrayList<Pair<String, GeoLocation>>()

            geoQuery.addGeoQueryEventListener(object : GeoQueryEventListener {
                override fun onGeoQueryReady() {
                    // We should always remove listener, because this listener changes array list.
                    // Listener can produce ConcurrentModificationException.
                    geoQuery.removeGeoQueryEventListener(this)
                    it.resume(keysList)
                }

                override fun onKeyEntered(key: String?, location: GeoLocation?) {
                    keysList.add(key!! to location!!)
                }

                override fun onKeyMoved(key: String?, location: GeoLocation?) {
                }

                override fun onKeyExited(key: String?) {
                    keysList.removeIf { pair -> pair.first == key }
                }

                override fun onGeoQueryError(error: DatabaseError?) {
                    it.resumeWithException(Exception("There is a problem with fetching chats!"))
                }

            })
        }

suspend fun chatKeysInRadius(radius: Double, location: Location): ArrayList<Pair<String, GeoLocation>> {
    val geoFire = GeoFire(sChatLocationNode.toDatabaseRef())
    val query = geoFire.queryAtLocation(GeoLocation(location.latitude, location.longitude), radius)
    return suspendGeoQuery(query)
}