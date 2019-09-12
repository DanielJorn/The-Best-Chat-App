package com.danjorn.coroutines

import android.app.Application
import android.location.Location
import com.danjorn.utils.location.DefaultLocationListener
import com.yayandroid.locationmanager.LocationManager
import com.yayandroid.locationmanager.configuration.DefaultProviderConfiguration
import com.yayandroid.locationmanager.configuration.LocationConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun suspendLocation(application: Application): Location =
        suspendCoroutine {
            LocationManager.Builder(application)
                    .notify(object : DefaultLocationListener() {
                        override fun onLocationChanged(location: Location?) {
                            it.resume(location!!)
                        }

                        override fun onLocationFailed(type: Int) {
                            it.resumeWithException(Exception("Something went wrong with result code $type"))
                        }
                    })
                    .configuration(LocationConfiguration.Builder()
                            .useDefaultProviders(DefaultProviderConfiguration.Builder()
                                    .build())
                            .build())
                    .build()
                    .get()
        }