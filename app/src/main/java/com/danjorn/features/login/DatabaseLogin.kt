package com.danjorn.features.login

import com.danjorn.core.authentication.UserEntity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface DatabaseLogin {
    suspend fun userExists(user: UserEntity): Boolean
    suspend fun passwordCorrect(user: UserEntity): Boolean
    suspend fun signIn(user: UserEntity)
    fun signUp(user: UserEntity)

    class FirebaseLogin
    @Inject constructor() : DatabaseLogin {
        private val fbDatabase = FirebaseDatabase.getInstance()
        private val usersRef = fbDatabase.reference.child("users")

        override suspend fun userExists(user: UserEntity): Boolean {
            val query = usersRef.orderByChild("username").equalTo(user.username)

            return suspendCoroutine { cont ->
                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) = cont.resume(snapshot.exists())

                    override fun onCancelled(error: DatabaseError) = cont.resumeWithException(Throwable()) //TODO Implement failure
                })
            }
        }

        override suspend fun passwordCorrect(user: UserEntity): Boolean {
            val query = usersRef.orderByChild("username").equalTo(user.username).limitToFirst(1)

            return suspendCoroutine { cont ->
                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        // if snapshot doesn't exist then user doesn't exist
                        if (!snapshot.exists())
                            cont.resume(false)

                        val resultSnapshot = snapshot.children.first()
                        val fetchedUser = resultSnapshot.getValue(UserEntity::class.java)!!
                        cont.resume(fetchedUser.password == user.password)
                    }

                    override fun onCancelled(error: DatabaseError) = cont.resumeWithException(Throwable()) //TODO Implement failure
                })
            }
        }

        override suspend fun signIn(user: UserEntity) {
            
        }

        override fun signUp(user: UserEntity) {
            val newUserId = usersRef.push()
            newUserId.setValue(user)
        }

    }
}