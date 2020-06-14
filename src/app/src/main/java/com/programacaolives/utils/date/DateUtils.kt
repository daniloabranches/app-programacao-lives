package com.programacaolives.utils.date

import android.content.Context
import com.programacaolives.R
import com.programacaolives.log.Log
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        fun extractFormattedDate(
            context: Context,
            simpleDateFormat: SimpleDateFormat,
            date: Date
        ): String {
            return try {
                when {
                    isToday(
                        date
                    ) -> context.getString(R.string.today)
                    isTomorrow(
                        date
                    ) -> context.getString(R.string.tomorrow)
                    else -> {
                        val calendar = Calendar.getInstance().also { it.time = date }
                        val formattedDate = simpleDateFormat.format(date)
                        val dayOfWeekIndex = calendar.get(Calendar.DAY_OF_WEEK)
                        val dayOfWeek =
                            getDayOfWeek(
                                context,
                                dayOfWeekIndex
                            )

                        "${formattedDate}, $dayOfWeek"
                    }
                }
            } catch (e: Exception) {
                Log.exception(e)
                ""
            }
        }

        private fun isToday(dateToCompare: Date): Boolean {
            val today =
                DateCalendar(Date())
            val date = DateCalendar(
                dateToCompare
            )

            return (today.dayOfMonth == date.dayOfMonth && today.month == date.month && today.year == date.year)
        }

        private fun isTomorrow(dateToCompare: Date): Boolean {
            val tomorrow = DateCalendar(
                Date()
            ).also {
                it.calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            val date = DateCalendar(
                dateToCompare
            )

            return (tomorrow.dayOfMonth == date.dayOfMonth && tomorrow.month == date.month && tomorrow.year == date.year)
        }

        private fun getDayOfWeek(context: Context, dayOfWeekIndex: Int): String {
            context.run {
                val daysOfWeek = listOf(
                    "",
                    getString(R.string.sunday),
                    getString(R.string.monday),
                    getString(R.string.tuesday),
                    getString(R.string.wednesday),
                    getString(R.string.thursday),
                    getString(R.string.friday),
                    getString(R.string.saturday)
                )
                return daysOfWeek[dayOfWeekIndex]
            }
        }

        fun extractFormattedHour(date: Date): String {
            return try {
                val calendar = Calendar.getInstance().also { it.time = date }
                val hour = calendar.get(Calendar.HOUR_OF_DAY)

                return when (val minute = calendar.get(Calendar.MINUTE)) {
                    0 -> "${hour}h"
                    in 1..9 -> "${hour}h0${minute}"
                    else -> "${hour}h${minute}"
                }
            } catch (e: Exception) {
                Log.exception(e)
                ""
            }
        }
    }

    class DateCalendar(
        private val date: Date
    ) {
        val calendar: Calendar by lazy { Calendar.getInstance().also { it.time = date } }
        val dayOfMonth: Int by lazy { calendar.get(Calendar.DAY_OF_MONTH) }
        val month: Int by lazy { calendar.get(Calendar.MONTH) }
        val year: Int by lazy { calendar.get(Calendar.YEAR) }
    }
}