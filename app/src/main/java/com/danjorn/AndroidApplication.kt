package com.danjorn

import androidx.multidex.MultiDexApplication
import com.danjorn.core.di.ApplicationComponent
import com.danjorn.core.di.ApplicationModule
import com.danjorn.core.di.DaggerApplicationComponent

class AndroidApplication : MultiDexApplication() {

    val appComponent : ApplicationComponent by lazy(LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
    }

    private fun injectMembers() = appComponent.inject(this)
}