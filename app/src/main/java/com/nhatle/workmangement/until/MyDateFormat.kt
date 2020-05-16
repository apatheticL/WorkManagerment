package com.nhatle.workmangement.until

import android.widget.TextView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun convertStringToDate(string: String): Date {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    var date: Date? = null
    try {
        date = dateFormat.parse(string)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return date!!
}

fun checkTimeEndDone(time: Date, texttimeStartAdd: TextView): Boolean {
    if (texttimeStartAdd.text.isNotEmpty()) {
        if (convertStringToDate(texttimeStartAdd.text.toString()) < time) {
            return true
        }
        if (convertStringToDate(texttimeStartAdd.text.toString()) > time) {
            return false
        }
    }
    return false
}

fun checkTimeEndActionSmallToDone(timeEndActionSmall: Date, timeEndAction: Date): Boolean {
    if (timeEndActionSmall < timeEndAction) {
        return true
    }
    return false
}

fun checkTimeStartActionSmallDone(
    timeStartActionSmall: Date,
    timeStartAction: Date,
    timeEndAction: Date
): Boolean {
    if (timeStartActionSmall in timeStartAction..timeEndAction) {
        return true
    }
    return false
}