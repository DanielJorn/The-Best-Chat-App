package com.danjorn.ui.chatRoom.message

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import kotlinx.android.synthetic.main.sender_avatar.view.*
import kotlinx.android.synthetic.main.text_of_message.view.*

abstract class BaseMessageView : FrameLayout {

    constructor(context: Context) : super(context) {
        inflateView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        inflateView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
            attrs, defStyleAttr) {
        inflateView(context)
    }

    private fun inflateView(context: Context) {
        LayoutInflater.from(context).inflate(getLayoutId(), this, true)
    }

    @LayoutRes
    abstract fun getLayoutId() : Int

    fun getMessageTextView(): TextView{
        return text_part
    }

    fun getUserImage(): ImageView?{
        return sender_avatar
    }

    fun getMessageSendTime(): TextView?{
        return date_text
    }

}