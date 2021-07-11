package com.example.myvoozkotlin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import com.example.myvoozkotlin.databinding.DialogFragmentCalendarBinding
import com.example.myvoozkotlin.databinding.FragmentHomeBinding
import com.example.myvoozkotlin.home.helpers.OnDatePicked
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class DatePickerDialogFragment(var calendar: Calendar, val onDatePicked: OnDatePicked): BottomSheetDialogFragment() {
    private var _binding: DialogFragmentCalendarBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(calendar: Calendar, onDatePicked: OnDatePicked): DatePickerDialogFragment {
            return DatePickerDialogFragment(calendar, onDatePicked)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogFragmentCalendarBinding.inflate(inflater, container, false)

        binding.calendarView.date = calendar.timeInMillis
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            run {
                calendar.set(year, month, dayOfMonth)
                onDatePicked.onDateCalendarClick(calendar)
                dismiss()
            }
        }
        return binding.root
    }
}