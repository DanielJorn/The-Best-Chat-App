package com.danjorn.ui.chatRoom

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.danjorn.models.UIMessage
import com.danjorn.presentation.chatRoom.ChatRoomViewModel
import com.danjorn.ui.chatRoom.list.MessageAdapter
import com.danjorn.views.R
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity() {

    private val tag = ChatRoomActivity::class.java.simpleName

    private lateinit var viewModel: ChatRoomViewModel

    private val messageAdapter = MessageAdapter(this)

    private lateinit var messageLiveData: LiveData<List<UIMessage>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        viewModel = ViewModelProviders.of(this).get(ChatRoomViewModel::class.java)

        messageLiveData = viewModel.messageLiveData
        message_recycler.adapter = messageAdapter

        messageLiveData.observe(this, Observer { handleNewMessageSet(it) })
        //get chat room
        val chatId = intent.getStringExtra(BUNDLE_CHAT_ID)

        viewModel.loadTenMessages()
    }

    private fun handleNewMessageSet(newMessageSet: List<UIMessage>) {
        Log.d(tag, "handleNewMessageSet: chats got. Chats are $newMessageSet")
        messageAdapter.submitList(newMessageSet)
    }

    companion object {
        const val BUNDLE_CHAT_ID = "chatId"
    }
}
