package com.danjorn.ui.chatRoom.list

import androidx.recyclerview.widget.DiffUtil
import com.danjorn.models.UIMessage

class MessageItemDiffCallback : DiffUtil.ItemCallback<UIMessage>() {
    override fun areItemsTheSame(oldItem: UIMessage, newItem: UIMessage): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UIMessage, newItem: UIMessage): Boolean {
        return oldItem == newItem
    }

}
