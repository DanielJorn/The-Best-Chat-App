package com.danjorn.features.chat

import android.content.Context
import android.content.Intent
import com.danjorn.core.platform.BaseActivity
import com.danjorn.core.platform.BaseFragment
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions

class ChatListActivity: BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, ChatListActivity::class.java)
    }

    override fun fragment(): BaseFragment {
        return ChatListFragment()
    }
}