package com.danjorn.coroutines

import com.firebase.geofire.GeoLocation
import com.firebase.geofire.GeoQuery
import com.firebase.geofire.GeoQueryEventListener
import com.google.firebase.database.DatabaseError
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun getListOfKeys(geoQuery: GeoQuery): ArrayList<String> =
        suspendCoroutine {
            val keysList = ArrayList<String>()

            geoQuery.addGeoQueryEventListener(object : GeoQueryEventListener{
                override fun onGeoQueryReady() {
                    it.resume(keysList)
                }

                override fun onKeyEntered(key: String?, location: GeoLocation?) {
                    keysList.add(key!!)
                }

                override fun onKeyMoved(key: String?, location: GeoLocation?) {
                }

                override fun onKeyExited(key: String?) {
                    keysList.remove(key)
                }

                override fun onGeoQueryError(error: DatabaseError?) {
                    it.resumeWithException(Exception("There is a problem with fetching chats!"))
                }

            })
        }