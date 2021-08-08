package com.example.myvoozkotlin.home

import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.home.helpers.OnDatePicked
import com.example.myvoozkotlin.home.helpers.OnTimePicked
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class TimePickerDialogFragment(var calendar: Calendar, val onTimePicked: OnTimePicked) :
    BottomSheetDialogFragment(), OnTimeSetListener {

    companion object {
        fun newInstance(calendar: Calendar, onTimePicked: OnTimePicked): TimePickerDialogFragment {
            return TimePickerDialogFragment(calendar, onTimePicked)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(
            activity, R.style.AppTheme_Dialog_Theme, this, calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            DateFormat.is24HourFormat(activity)
        )
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        onTimePicked.onTimeClick(calendar)
        dismiss()
    }
}