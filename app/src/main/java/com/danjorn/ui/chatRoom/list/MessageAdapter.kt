package com.danjorn.ui.chatRoom.list

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danjorn.models.MessageResponse
import com.danjorn.ui.chatRoom.message.BaseMessageView
import com.danjorn.ui.chatRoom.message.ParticipateMessageView
import com.danjorn.ui.chatRoom.message.UserMessageView
import com.danjorn.utils.PicassoUtils
import java.util.*

class MessageAdapter(private val context: Context, private val messageResponseList: ArrayList<MessageResponse>) : RecyclerView.Adapter<MessageAdapter.MessageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val messageView : BaseMessageView

        when (viewType) {
            0 -> messageView = UserMessageView(context)
            1 -> messageView = ParticipateMessageView(context)
            else -> throw IllegalStateException("There is no view type with id $viewType")
        }

        messageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        return MessageHolder(messageView)
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2

    }

    override fun getItemCount(): Int {
        return messageResponseList.size

    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        holder.messageTextView.text = messageResponseList[position].messageText
        holder.sendTimeTextView?.text = Date(messageResponseList[position].timestamp!!).toString()
        PicassoUtils.commonImageDownload(messageResponseList[position].authorId, holder.imageView!!)

    }


    class MessageHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageTextView: TextView
        val imageView: ImageView?
        val sendTimeTextView: TextView?

        init {
            if (view !is BaseMessageView)
                throw IllegalArgumentException("You should use BaseMessageView class for creating MessageHolder")
            else {
                messageTextView = view.getMessageTextView()
                imageView = view.getUserImage()
                sendTimeTextView = view.getMessageSendTime()
            }
        }
    }
}