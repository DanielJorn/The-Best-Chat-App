package com.danjorn.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danjorn.models.db.ChatPojo
import com.danjorn.utils.PicassoUtils
import com.danjorn.views.R
import com.mikhaellopez.circularimageview.CircularImageView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat.view.*


class ChatAdapter(private val context: Context) :
        RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    private val chats: ArrayList<ChatPojo> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatPojo = chats[position]

        PicassoUtils.commonImageDownload(chatPojo.chatImageUrl, holder.chatImage)
        holder.chatTitle.text = chatPojo.title
        //PicassoUtils.commonImageDownload(chatPojo., holder.lastMessageSenderAvatar)

    }

    fun addOrUpdateChat(requestChat: ChatPojo) {
        if (chatExistsById(requestChat.id!!))
            updateChat(requestChat)
        else addChat(requestChat)
    }

    private fun addChat(requestChat: ChatPojo) {
        chats.add(requestChat)
        notifyItemInserted(chats.size)
    }

    private fun updateChat(requestChat: ChatPojo) {
        val toUpdate = findChatById(requestChat.id!!)
        toUpdate?.deepCopyFrom(requestChat)
        notifyItemChanged(chats.indexOf(toUpdate))
    }


    private fun chatExistsById(id: String): Boolean {
        return chats.any { it.id == id }
    }

    private fun findChatById(requestId: String): ChatPojo? {
        chats.forEach {
            if (it.id == requestId) return it
        }
        return null
    }

    class ChatViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        val chatImage: ImageView = containerView.image_chat
        val chatTitle: TextView = containerView.text_chat_title
        val lastMessageSenderAvatar: CircularImageView = containerView.image_last_message_sender_avatar
        val lastMessageText: TextView = containerView.text_last_message
    }
}
