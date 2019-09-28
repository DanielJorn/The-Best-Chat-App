package com.danjorn.coroutines

import com.danjorn.models.WithId
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Downloads data from given reference and automatically maps it to given class
 * @param databaseRef reference from which you want to download data
 * @param clazz class to map data from DataSnapshot. If this is [WithId], maps id from snapshot
 * */
suspend fun <T> downloadFrom(databaseRef: DatabaseReference, clazz: Class<T>): T? =
        suspendCoroutine {
            databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    it.resumeWithException(error.toException())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val result = snapshot.getValue(clazz)
                    if (result is WithId)
                        result.id = snapshot.key
                    it.resume(result)
                }
            })
        }