package com.example.homelibrary.model

import com.example.myvoozkotlin.helpers.Utils
import com.google.gson.annotations.SerializedName
import java.text.ParseException
import java.util.*

class EntryLink(
        @SerializedName("date") val date:String,
        @SerializedName("code") val code: String,
        @SerializedName("is_lock") val isLock: Boolean
    ){
    fun getDate(): Calendar? {
        var calendar: Calendar? = null
        try {
            calendar =
                Utils.stringToCalendar(date, TimeZone.getTimeZone("Europe/Moscow"))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return calendar
    }
}