package com.danjorn.utils.liveData

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

/**
 * This class is used for observing data from Firebase Database. We can configure which children we
 * want to observe.
 * @property rootObserveRef is the root of observations. We don't observe all data under this node,
 * but only children that we have added using [addUpdateListener].
 */
class FirebaseObserveLiveData(private val rootObserveRef: DatabaseReference) : LiveData<DataSnapshot>() {

    private val childrenToObserve = ArrayList<DatabaseReference>()

    override fun onActive() {
        childrenToObserve.forEach {
            it.addValueEventListener(updateLiveDataListener)
        }
    }

    override fun onInactive() {
        childrenToObserve.forEach {
            it.removeEventListener(updateLiveDataListener)
        }
    }

    fun addUpdateListener(childPath: String) {
        val child = rootObserveRef.child(childPath)
        child.addValueEventListener(updateLiveDataListener)
        childrenToObserve.add(child)
    }

    fun removeUpdateListener(childPath: String) {
        val child = rootObserveRef.child(childPath)
        child.removeEventListener(updateLiveDataListener)
        childrenToObserve.remove(child)
    }

    private val updateLiveDataListener = object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {

        }

        override fun onDataChange(snapshot: DataSnapshot) {
            this@FirebaseObserveLiveData.value = snapshot
        }
    }
}