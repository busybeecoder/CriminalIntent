package com.bignerdranch.android.criminalintent

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*
import java.util.Calendar.*

private const val ARG_TIME = "time"

class TimePickerFragment : DialogFragment() {

    interface Callbacks {
        fun onTimeSelected(date: Date)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments?.getSerializable(ARG_TIME) as Date
        val calendar = getInstance()
        calendar.time = date
        val calendarYear = calendar.get(YEAR)
        val calendarMonth = calendar.get(MONTH)
        val calendarDay = calendar.get(DAY_OF_MONTH)

        val timeListener =
            TimePickerDialog.OnTimeSetListener { _: TimePicker, hour: Int, minute: Int ->
                val resultDate: Date =
                    GregorianCalendar(calendarYear, calendarMonth, calendarDay, hour, minute).time
                targetFragment?.let { fragment ->
                    (fragment as Callbacks).onTimeSelected(resultDate)
                }
            }

        val initialHour = calendar.get(HOUR_OF_DAY)
        val initialMinute = calendar.get(MINUTE)
        val is24HourView = false

        return TimePickerDialog(
            requireContext(),
            timeListener,
            initialHour,
            initialMinute,
            is24HourView
        )
    }

    companion object {
        fun newInstance(date: Date): TimePickerFragment {
            val args = Bundle().apply {
                putSerializable(ARG_TIME, date)
            }

            return TimePickerFragment().apply {
                arguments = args
            }
        }
    }
}