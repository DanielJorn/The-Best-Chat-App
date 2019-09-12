package com.danjorn.views.custom.message

import android.content.Context
import android.util.AttributeSet
import com.danjorn.views.R

class ParticipateMessageView : BaseMessageView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
            defStyleAttr)

    override fun getLayoutId(): Int {
        return R.layout.participate_messasge
    }
}
