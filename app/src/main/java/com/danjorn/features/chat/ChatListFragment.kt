package com.danjorn.features.chat

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.danjorn.core.permission.PermissionFragment.Companion.TAG
import com.danjorn.core.platform.BaseFragment
import com.danjorn.views.R
import com.google.firebase.database.FirebaseDatabase

class ChatListFragment : BaseFragment() {

    private lateinit var chatListViewModel: ChatListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        chatListViewModel = viewModelFactory.create(ChatListViewModel::class.java)
        chatListViewModel.failure.observe(this, Observer { ::handleFailure })
        chatListViewModel.permissionGranted.observe(this, Observer { })
        chatListViewModel.requestLocationPermission(this)

        val fb = FirebaseDatabase.getInstance()
        val rootRef = fb.reference
        rootRef.setValue("test value")
        Log.d(TAG, "onCreate: called database")
    }

    private fun handleFailure(){

    }

    private fun permissionPermanentlyDenied(){
        Log.d("TEST", "permissionPermanentlyDenied()")
    }

    private fun permissionShouldBeRational(){
        Log.d("TEST", "permissionShouldBeRational()")
    }

    override fun layoutId(): Int {
        return R.layout.fragment_chat_list
    }
}