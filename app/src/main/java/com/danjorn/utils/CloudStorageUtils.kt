package com.danjorn.utils

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

object CloudStorageUtils {

    fun uploadFile(databasePath: String, uri: Uri, successCallback: (UploadTask.TaskSnapshot) -> Unit) {

        val storageRef = FirebaseStorage.getInstance().reference
        val ref = storageRef.child(databasePath)

        ref.putFile(uri).addOnSuccessListener(successCallback)

    }

    fun getDownloadURL(databasePath: String, successCallback : (Uri) -> Unit) {

        val reference = FirebaseStorage.getInstance().reference.child(databasePath)

        reference.downloadUrl.addOnSuccessListener(successCallback)
    }
}
