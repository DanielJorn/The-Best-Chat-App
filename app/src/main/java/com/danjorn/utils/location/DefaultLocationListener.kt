package com.danjorn.utils.location

import android.location.Location
import android.os.Bundle
import com.yayandroid.locationmanager.listener.LocationListener

/*
* This class allows us override only methods we need.
*/

open class DefaultLocationListener : LocationListener {

    override fun onLocationChanged(location: Location?) {

    }

    override fun onPermissionGranted(alreadyHadPermission: Boolean) {

    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }

    override fun onProcessTypeChanged(processType: Int) {

    }

    override fun onLocationFailed(type: Int) {

    }

}
