package com.faanghut.reflection

import android.annotation.SuppressLint
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