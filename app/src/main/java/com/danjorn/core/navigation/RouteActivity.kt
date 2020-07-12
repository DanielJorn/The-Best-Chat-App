package com.danjorn.core.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.danjorn.AndroidApplication
import com.danjorn.core.di.ApplicationComponent
import javax.inject.Inject

class RouteActivity: AppCompatActivity() {

    private val appComponent : ApplicationComponent by lazy(LazyThreadSafetyMode.NONE) {
        (application as AndroidApplication).appComponent
    }

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        navigator.openMain(this)
    }
}