package com.danjorn.utils

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

class OverflowTextWatcher(private val textInputLayout: TextInputLayout) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (isOverflowed())
            textInputLayout.error = "Overflowed! Please input on ${getText().length - textInputLayout.counterMaxLength} character(s) less"
        else if (textInputLayout.error != null) textInputLayout.error = null

    }

    private fun getText(): String {
        return textInputLayout.editText!!.text.toString()
    }

    private fun isOverflowed(): Boolean {
        return textInputLayout.counterMaxLength < getText().length
    }

}