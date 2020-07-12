package com.danjorn.features.chat

import android.Manifest
import androidx.lifecycle.MutableLiveData
import com.danjorn.core.exception.Failure
import com.danjorn.core.interactor.UseCase.None
import com.danjorn.core.permission.RequestPermissions
import com.danjorn.core.permission.RequestPermissions.*
import com.danjorn.core.platform.BaseViewModel
import javax.inject.Inject

class ChatListViewModel
@Inject constructor(private val getChats: GetChats,
                    private val requestPermissions: RequestPermissions) : BaseViewModel() {

    var chats: MutableLiveData<List<ChatView>> = MutableLiveData()
    var permissionGranted: MutableLiveData<None> = MutableLiveData()

    fun requestLocationPermission(fragment: ChatListFragment) {
        requestPermissions(PermissionParams(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), fragment)
        ) { it.either(::handleFailure, ::handlePermissionGranted) }
    }

    private fun handlePermissionGranted(none: None) {
        permissionGranted.value = none
    }

    fun getChats() = getChats(None()) { it.either(::handleFailure, ::handleChatList) }

    private fun handleChatList(chats: List<Chat>) {
        this.chats.value = chats.map { ChatView(it.title) }
    }
}