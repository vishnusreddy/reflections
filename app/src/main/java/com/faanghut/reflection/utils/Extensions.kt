package com.faanghut.reflection.utils

import android.annotation.SuppressLint
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.ContextWrapper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.time.LocalTime

@SuppressLint("SimpleDateFormat")
fun LocalTime.to12HourFormat(): String {
    val timeString = "$hour:$minute"
    val sdf24 = SimpleDateFormat("HH:mm")
    val sdf12 = SimpleDateFormat("hh:mm aa")
    val time = sdf24.parse(timeString)
    return sdf12.format(time).toString().uppercase()
}

fun LocalTime.to24HourFormat(): String {
    return "$hour : $minute"
}

fun EditText.showKeyboard() {
    if (requestFocus()) {
        (getActivity()?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(this, SHOW_IMPLICIT)
        setSelection(text.length)
    }
}

fun View.getActivity(): AppCompatActivity?{
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}