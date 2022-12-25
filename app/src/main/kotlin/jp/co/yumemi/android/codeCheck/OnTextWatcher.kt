package jp.co.yumemi.android.codeCheck

import android.text.Editable
import android.text.TextWatcher

interface OnTextWatcher : TextWatcher {
    override fun afterTextChanged(p0: Editable?) {}

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
}