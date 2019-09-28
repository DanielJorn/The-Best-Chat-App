package com.danjorn.ui.createChat.input

import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import com.danjorn.views.R
import com.google.android.material.textfield.TextInputLayout

/**
 * Encapsulates TextInputEditText with only one line for input. Use it when you have to create short one-line edit
 * texts with all power of TextInputLayout
 */
class OneLineInputLayout : TextInputLayout {

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }


    private fun init(attrs: AttributeSet?, defStyle: Int) {
        inflate(context, R.layout.one_line_input_layout, this)
        val a = context.obtainStyledAttributes(attrs, R.styleable.OneLineInputLayout, defStyle, 0)

        val inputType = a.getInt(R.styleable.OneLineInputLayout_android_inputType, EditorInfo.TYPE_NULL)

        if (inputType != EditorInfo.TYPE_NULL)
            editText?.inputType = inputType
        a.recycle()
    }

    fun getText(): String {
        return editText!!.text.toString()
    }

}

