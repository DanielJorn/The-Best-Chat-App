package com.danjorn.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.danjorn.models.MessagePojo
import com.danjorn.views.R
import com.danjorn.views.adapters.MessageAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity() {

    private val tag = ChatRoomActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        //get chat room
        val roomId = intent.getStringExtra("room")

        //get ref

        val rootRef = FirebaseDatabase.getInstance().reference
        val messagesRoot = rootRef.child("messages")
        val chatMessages = messagesRoot.child(roomId)

        val pojoList = ArrayList<MessagePojo>()

        //listener that will fetch MessagePojo
        val valueListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.map {
                    val pojo = it.getValue(MessagePojo::class.java)
                    pojo!!.messageId = it.key
                    pojo.chatId = roomId //TODO Find how to place chat id here and do I need that id
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
