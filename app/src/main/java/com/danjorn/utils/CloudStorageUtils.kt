package com.danjorn.utils

import android.net.Uri
import android.util.Log
import com.danjorn.ktx.toFirebaseStorageRef
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val tag = "CloudStorageUtils" //TODO How to use name of this class as tag using <class>::class.java.getSimpleName?

suspend fun uploadFile(storageRef: String, uri: Uri): Unit? = suspendCoroutine { cont ->
    val ref = storageRef.toFirebaseStorageRef()

    ref.putFile(uri).addOnSuccessListener {
        Log.d(tag, "uploadFile: success. Path $storageRef")
        cont.resume(null)
    }.addOnFailureListener {
        cont.resumeWithException(it)
    }
}

suspend fun getDownloadURL(storagePath: String): String = suspendCoroutine { cont ->
    val reference = storagePath.toFirebaseStorageRef()
    reference.downloadUrl.addOnSuccessListener { cont.resume(it.toString()) }
}
