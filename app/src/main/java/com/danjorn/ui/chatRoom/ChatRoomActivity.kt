package com.danjorn.ui.chatRoom

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.danjorn.database.MESSAGES
import com.danjorn.ktx.toDatabaseRef
import com.danjorn.models.MessageResponse
import com.danjorn.ui.chatRoom.list.MessageAdapter
import com.danjorn.views.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity() {

    companion object {
        const val BUNDLE_CHAT_ID = "chatId"
    }

    private val tag = ChatRoomActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        //get chat room
        val chatId = intent.getStringExtra(BUNDLE_CHAT_ID)

        //get ref

        val chatMessages = "$MESSAGES/$chatId".toDatabaseRef()

        val pojoList = ArrayList<MessageResponse>()

        //listener that will fetch MessageResponse
        val valueListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.map {
                    val pojo = it.getValue(MessageResponse::class.java)
                    pojo!!.id = it.key
                    pojo.chatId = chatId //TODO Find how to place chat id here and do I need that id
                    pojoList.add(pojo)
                }
                message_recycler.adapter = MessageAdapter(this@ChatRoomActivity.applicationContext,
                        pojoList)
                Log.d(tag, "onDataChange: complete")
            }
        }

        //create query that gets last 10 messages
        val query = chatMessages.limitToLast(10)

        //get last 10 messages
        query.addListenerForSingleValueEvent(valueListener)

    }
}
