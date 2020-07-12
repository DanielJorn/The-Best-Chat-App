package com.danjorn.features.chat

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseService
@Inject constructor(private val database: FirebaseDatabase){
    companion object {
        private const val CHATS_NODE = "chats"
    }

    fun chats(): DatabaseReference = database.getReference(CHATS_NODE)
}