package com.dk.login.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.dk.login.R

class PhoneValidator(private val editText: EditText) : TextWatcher {

    private var isBlocked = false

    override fun beforeTextChanged(
        s: CharSequence?, start: Int, count: Int, after: Int
    ) {
        editText.hint = PHONE_MASK

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s == null || isBlocked) {
            return
        }

        val preparedStr = s.replace(Regex("(\\D*)"), "")
        val resultStr = StringBuilder("+7 ")
        for (i in 1 until preparedStr.length) {
            if (i == 4 || i == 7 || i == 9) {
                resultStr.append(" ")
            }
            resultStr.append(preparedStr[i])
        }

        isBlocked = true
        val isEdit = editText.selectionStart != editText.length()
        editText.setText(resultStr)
        if (isEdit) {
            editText.setSelection(if (start > resultStr.length) resultStr.length else start)
        } else {
            editText.setSelection(resultStr.length)
        }
        isBlocked = false
    }

    override fun afterTextChanged(s: Editable?) {
        editText.hint = editText.context.resources.getString(R.string.phone_number)
    }

    companion object {
        private const val PHONE_MASK = "+7 XXX XXX XX XX"
    }
}