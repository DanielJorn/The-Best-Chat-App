package com.danjorn.core.navigation

import android.content.Context
import com.danjorn.features.login.Authenticator
import com.danjorn.features.login.LoginActivity
import com.danjorn.features.chat.ChatListActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator
@Inject constructor(private val authenticator: Authenticator) {

    private fun openChatList(context: Context) = context.startActivity(ChatListActivity.callingIntent(context))

    fun openMain(context: Context){
        when(authenticator.userLoggedIn()){
            true -> openChatList(context)
            false -> openLogin(context)
        }
    }

    private fun openLogin(context: Context) = context.startActivity(LoginActivity.callingIntent(context))

}