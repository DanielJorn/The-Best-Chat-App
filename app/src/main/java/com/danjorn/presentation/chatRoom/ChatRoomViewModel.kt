package com.danjorn.presentation.chatRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danjorn.firebase.database.MESSAGES
import com.danjorn.ktx.getValueAndId
import com.danjorn.ktx.toDatabaseRef
import com.danjorn.models.MessageResponse
import com.danjorn.models.UIMessage
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ChatRoomViewModel : ViewModel() {
    private val tag = ChatRoomViewModel::class.java.simpleName

    private val chatId = "TestChat" //TODO Replace it with something meaningful

    private val _messageLiveData = MutableLiveData<List<UIMessage>>()
    val messageLiveData: LiveData<List<UIMessage>> = _messageLiveData

    fun loadTenMessages() {
        val chatMessages = "$MESSAGES/$chatId".toDatabaseRef()

        //listener that will fetch MessageResponse
        val valueListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) = Unit

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val messagesList = dataSnapshot.children.map {
                    val messageResponse = it.getValueAndId(MessageResponse::class.java)!!
                    messageResponseToUIMessage(messageResponse)
                }
                _messageLiveData.postValue(messagesList)
            }
        }

        //create query that gets last 10 messages
        val query = chatMessages.limitToLast(10)

        //get last 10 messages
        query.addListenerForSingleValueEvent(valueListener)
    }

    fun messageResponseToUIMessage(response: MessageResponse): UIMessage {
        response.chatId = chatId
        return response.toUIMessage()
    }
}