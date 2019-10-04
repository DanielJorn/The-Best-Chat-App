package com.danjorn.ui.chatRoom.list

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.danjorn.models.UIMessage
import com.danjorn.ui.chatRoom.message.BaseMessageView
import com.danjorn.ui.chatRoom.message.ParticipateMessageView
import com.danjorn.ui.chatRoom.message.UserMessageView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(private val context: Context)
    : ListAdapter<UIMessage, MessageAdapter.MessageHolder>(MessageItemDiffCallback()) {

    private val tag = MessageAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val messageView = createMessageViewFrom(viewType)
        return MessageHolder(messageView)
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        bindMe(holder, getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getItemType(getItem(position))
    }

    private fun getItemType(item: UIMessage): Int {
        if (item.authorUser.id == FirebaseAuth.getInstance().uid)
            return USER_MESSAGE
        else return PARTICIPATE_MESSAGE
    }

    private fun bindMe(holder: MessageHolder, message: UIMessage) {
        holder.messageTextView.text = message.messageText
    }

    private fun createMessageViewFrom(viewType: Int): BaseMessageView {
        val messageView = getMessageView(viewType, context)
        setLayoutParams(messageView)

        return messageView
    }

    /**
     * Call of this function is necessary. Without it, constraint layout children with width/height
     * 0dp (match_constraint) will not be displayed.
     * */
    private fun setLayoutParams(messageView: BaseMessageView) {
        messageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    class MessageHolder(messageView: BaseMessageView) : RecyclerView.ViewHolder(messageView) {
        val messageTextView: TextView = messageView.getMessageTextView()
    }

    companion object {
        const val USER_MESSAGE = 0
        const val PARTICIPATE_MESSAGE = 1

        private fun getMessageView(viewType: Int, context: Context): BaseMessageView {
            when (viewType) {
                USER_MESSAGE -> return UserMessageView(context)
                PARTICIPATE_MESSAGE -> return ParticipateMessageView(context)
                else -> throw IllegalArgumentException("There is no view with a view type $viewType")
            }
        }
    }
}