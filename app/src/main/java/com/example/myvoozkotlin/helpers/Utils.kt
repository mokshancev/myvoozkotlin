package com.example.myvoozkotlin.helpers

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.myvoozkotlin.BaseApp
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

    fun getMonthName(position: Int): String{
        when(position){
            0 -> return "Янв"
            1 -> return "Фев"
            2 -> return "Мар"
            3 -> return "Апр"
            4 -> return "Май"
            5 -> return "Июн"
            6 -> return "Июл"
            7 -> return "Авг"
            8 -> return "Сен"
            9 -> return "Окт"
            10 -> return "Ноя"
            else -> return "Дек"
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