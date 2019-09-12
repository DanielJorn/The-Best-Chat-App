package com.danjorn.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danjorn.models.database.ChatPojo
import com.danjorn.utils.PicassoUtils
import com.danjorn.views.R

import com.mikhaellopez.circularimageview.CircularImageView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_chat_adapter.view.*


class ChatAdapter(private val context: Context, private var chats: ArrayList<ChatPojo>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.row_chat_adapter, parent, false)
        return ChatViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatPojo = chats[position]

        PicassoUtils.commonImageDownload(chatPojo.chatImageUrl, holder.chatImage)
        holder.chatTitle.text = chatPojo.title
        //TODO PicassoUtils.commonImageDownload(chatPojo., holder.lastMessageSenderAvatar)

    }

    fun setList(list : ArrayList<ChatPojo>){
        chats.clear()
        chats = list.clone() as ArrayList<ChatPojo>
        this.notifyDataSetChanged()
    }

    class ChatViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        val chatImage: ImageView = containerView.image_chat
        val chatTitle: TextView = containerView.text_chat_title
        val lastMessageSenderAvatar: CircularImageView = containerView.image_last_message_sender_avatar
        val lastMessageText: TextView = containerView.text_last_message
    }
}
