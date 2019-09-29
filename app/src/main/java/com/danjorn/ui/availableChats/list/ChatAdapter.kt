package com.danjorn.ui.availableChats.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danjorn.models.UIChat
import com.danjorn.utils.PicassoUtils
import com.danjorn.views.R
import com.mikhaellopez.circularimageview.CircularImageView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat.view.*


class ChatAdapter(private val context: Context) :
        RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    private val chats: ArrayList<UIChat> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatPojo = chats[position]

        PicassoUtils.commonImageDownload(null, holder.chatImage)
        holder.chatTitle.text = chatPojo.chatTitle
        //PicassoUtils.commonImageDownload(chatPojo., holder.lastMessageSenderAvatar)

    }

    fun addOrUpdateChat(requestUIChat: UIChat) {
        if (chatExistsById(requestUIChat.id!!))
            updateChat(requestUIChat)
        else addChat(requestUIChat)
    }

    private fun addChat(requestUIChat: UIChat) {
        chats.add(requestUIChat)
        notifyItemInserted(chats.size)
    }

    private fun updateChat(requestUIChat: UIChat) {
        val toUpdate = findChatById(requestUIChat.id!!)
        toUpdate?.deepCopyFrom(requestUIChat)
        notifyItemChanged(chats.indexOf(toUpdate))
    }


    private fun chatExistsById(id: String): Boolean {
        return chats.any { it.id == id }
    }

    private fun findChatById(requestId: String): UIChat? {
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
