package com.danjorn.features.chat

import com.danjorn.core.exception.Failure
import com.danjorn.core.exception.Failure.DatabaseError
import com.danjorn.core.exception.Failure.NetworkFailure
import com.danjorn.core.extension.get
import com.danjorn.core.functional.Either
import com.danjorn.core.functional.Either.*
import com.danjorn.core.platform.NetworkHandler
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import javax.inject.Inject

interface ChatRepository {
    suspend fun chats(): Either<Failure, List<Chat>>

    class Database
    @Inject constructor(private val firebaseService: FirebaseService,
                        private val networkHandler: NetworkHandler) : ChatRepository {

        override suspend fun chats(): Either<Failure, List<Chat>> {
            val chats = firebaseService.chats()
            return when (networkHandler.isConnected){
                true -> request(firebaseService.chats())
                else -> Left(NetworkFailure)
            }
        }

        private suspend fun request(chats: DatabaseReference): Either<Failure, List<Chat>> {
            //val snapshotList = chats.get() ?: return Left(DatabaseError)
            //val chatList = snapshotList.children.mapNotNull { it.getValue(Chat::class.java) }

            return Right(listOf())
        }
    }
}