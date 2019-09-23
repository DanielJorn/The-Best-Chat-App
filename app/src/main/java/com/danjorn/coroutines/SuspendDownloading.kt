package com.danjorn.coroutines

import com.danjorn.models.WithId
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <T> suspendDownload(databaseRef: DatabaseReference, clazz: Class<T>): T? =
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