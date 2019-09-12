package com.danjorn.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.danjorn.coroutines.suspendLocation
import com.danjorn.models.database.ChatPojo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var liveData: MutableLiveData<ArrayList<ChatPojo>>? = null
    private var chatPojoList: ArrayList<ChatPojo> = ArrayList()

    private val tag = MainViewModel::class.java.simpleName

    fun updateAvailableChats(): MutableLiveData<ArrayList<ChatPojo>>? {
        loadChats()
        if (liveData == null)
            liveData = MutableLiveData()
        return liveData
    }

    private fun loadChats() {
        val chats = ArrayList<String>()

        GlobalScope.launch {
            suspendLocation(getApplication())
        }
    }
}

/* val geofireRef = FirebaseDatabase.getInstance().getReference(chatLocation)
   val geoFire = GeoFire(geofireRef)
   val geoQuery = geoFire.queryAtLocation(GeoLocation(location!!.latitude,
           location.longitude), MAX_RADIUS.toDouble())

   geoQuery.addGeoQueryEventListener(object : GeoQueryEventListener {

       override fun onGeoQueryReady() {
           //All data is ready, use it in adapter i think
           // No i don't think so no more. I should download 10 chats with their last messages! and then
           // i will post value this data

           chats.forEach {
               FirebaseDatabase.getInstance().reference.child("chats/$it")
                       .addValueEventListener(object : ValueEventListener {
                           override fun onCancelled(p0: DatabaseError) {


                           }

                           override fun onDataChange(chatSnapshot: DataSnapshot) {
                               Log.d(tag, "onDataChange: chatSnapshot gotten!")
                               chatPojoList.add(chatSnapshot.getValue(ChatPojo::class.java)!!)
                               liveData!!.postValue(chatPojoList)
                           }
                       })
           }
       }

       override fun onKeyEntered(key: String?, location: GeoLocation?) {
           // maybe check whatever chat is cached or not? Or it caches without effort?
           chats.add(key!!)
       }

       override fun onKeyMoved(key: String?, location: GeoLocation?) {
           //Nothing
       }

       override fun onKeyExited(key: String?) {
           //Delete it from recycler view
           // можливо видалити його нахрін... з арай листа й сказать пост вальює...
       }

       override fun onGeoQueryError(error: DatabaseError?) {
       }
   })
}
}*/