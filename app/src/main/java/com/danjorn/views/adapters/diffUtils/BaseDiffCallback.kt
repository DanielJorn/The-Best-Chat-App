package com.danjorn.views.adapters.diffUtils

import androidx.recyclerview.widget.DiffUtil
import com.danjorn.models.WithId

class BaseDiffCallback(private val oldList: List<WithId>,
                       private val newList: List<WithId>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

}