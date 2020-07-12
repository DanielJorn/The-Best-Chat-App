package com.danjorn.core.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.danjorn.views.R
import com.danjorn.views.R.id.fragmentContainer
import kotlinx.android.synthetic.main.toolbar.*

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)
        setSupportActionBar(toolbar)
        addFragment(savedInstanceState)
    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(
                fragmentContainer) as BaseFragment).onBackPressed()
        super.onBackPressed()
    }

    private fun addFragment(savedBundle: Bundle?) {
        savedBundle ?: supportFragmentManager.beginTransaction().add(
                fragmentContainer, fragment()).commit()
    }

    abstract fun fragment(): BaseFragment
}