package com.danjorn.core.platform

import android.content.Context
import com.danjorn.core.extension.networkInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHandler
@Inject constructor(private val context: Context){
    val isConnected get() = context.networkInfo?.isConnectedOrConnecting
}