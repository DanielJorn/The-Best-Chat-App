package com.danjorn.core.extension

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun DatabaseReference.get(): DataSnapshot? {
    return suspendCoroutine {
        addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) = it.resume(null)
            override fun onDataChange(snapshot: DataSnapshot) = it.resume(snapshot)
        })
    }
}