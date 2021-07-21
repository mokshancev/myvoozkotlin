package com.example.myvoozkotlin.helpers

import android.widget.Toast
import java.util.*

object Utils {
    fun getDayName(position: Int): String{
        when(position){
            0 -> return "Пн"
            1 -> return "Вт"
            2 -> return "Ср"
            3 -> return "Чт"
            4 -> return "Пт"
            else -> return "Сб"
        }
    }

    fun getCalendarDayOfWeek(calendar: Calendar, position: Int): Calendar{
        var c = calendar.clone() as Calendar
        when(position){
            0 -> c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            1 -> c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
            2 -> c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
            3 -> c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY)
            4 -> c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
            else -> c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
        }
        return c
    }
}