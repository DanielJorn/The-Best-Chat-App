package com.danjorn.utils

import android.app.Activity
import android.location.Location
import com.danjorn.models.database.ChatPojo
import com.danjorn.utils.location.DefaultLocationListener
import com.firebase.geofire.GeoLocation
import com.firebase.geofire.core.GeoHash
import com.google.firebase.database.FirebaseDatabase
import com.yayandroid.locationmanager.LocationManager
import com.yayandroid.locationmanager.configuration.DefaultProviderConfiguration
import com.yayandroid.locationmanager.configuration.LocationConfiguration
import com.yayandroid.locationmanager.providers.locationprovider.DefaultLocationProvider
import java.util.*

private const val tag = "FirebaseUtils"

fun uploadChat(activity: Activity, chat: ChatPojo, successListener: (String) -> Unit, failureListener: (Exception) -> Unit) {

    LocationManager.Builder(activity.applicationContext)
            .activity(activity)
            .configuration(
                    LocationConfiguration.Builder()
                            .useDefaultProviders(
                                    DefaultProviderConfiguration.Builder()
                                            .gpsMessage("enable gps u freaking dump")
                                            .build()
                            )
                            .build()
            )
            .locationProvider(DefaultLocationProvider())
            .notify(object : DefaultLocationListener() {
                override fun onLocationChanged(location: Location?) {

                    val chatsRef = FirebaseDatabase.getInstance().reference.child("chats")
                    val chatId = chatsRef.push().key
                    val geoLocation = GeoLocation(location!!.latitude, location.longitude)
                    val geoHash = GeoHash(geoLocation)
                    val map = HashMap<String, Any>()

                    map["chats/$chatId"] = chat
                    map["chatLocation/$chatId/g"] = geoHash.geoHashString
                    map["chatLocation/$chatId/l"] = Arrays.asList(location.latitude, location.longitude)
                    FirebaseDatabase.getInstance().reference.updateChildren(map)
                            .addOnSuccessListener{ successListener(chatId!!) }
                            .addOnFailureListener(failureListener)

                }
            })
            .build()
            .get()


}


fun getInRadius(databasePath: String, radius: Int) {

}
