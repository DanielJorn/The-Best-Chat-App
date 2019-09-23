package com.danjorn.coroutines

import android.app.Application
import android.location.Location
import com.danjorn.utils.location.DefaultLocationListener
import com.yayandroid.locationmanager.LocationManager
import com.yayandroid.locationmanager.configuration.DefaultProviderConfiguration
import com.yayandroid.locationmanager.configuration.LocationConfiguration
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun suspendLocation(application: Application): Location =
        suspendCancellableCoroutine {
            // I use cancelable coroutine because onLocationChanged can be triggered more than once
            LocationManager.Builder(application)
                    .notify(object : DefaultLocationListener() {
                        override fun onLocationChanged(location: Location?) {
                            if (!it.isCompleted)
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