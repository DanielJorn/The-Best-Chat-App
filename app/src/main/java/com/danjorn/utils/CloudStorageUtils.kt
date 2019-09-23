package com.danjorn.utils

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage

private const val tag = "CloudStorageUtils" //TODO How to use name of this class as tag using <class>::class.java.getSimpleName?

suspend fun uploadFile(databasePath: String, uri: Uri) {

    val storageRef = FirebaseStorage.getInstance().reference
    val ref = storageRef.child(databasePath)

    ref.putFile(uri).addOnSuccessListener { Log.d(tag, "uploadFile: success. Path $databasePath") }

}

fun getDownloadURL(databasePath: String, successCallback: (Uri) -> Unit) {

    val reference = FirebaseStorage.getInstance().reference.child(databasePath)

    reference.downloadUrl.addOnSuccessListener(successCallback)
}
