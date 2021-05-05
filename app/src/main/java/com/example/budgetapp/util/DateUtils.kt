package com.example.budgetapp.util

import android.util.Range
import com.example.budgetapp.database.Converters
import java.util.*

fun Calendar.findWeekRange(): Range<Date> {
    set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    val lastSunday: Date = Converters().fromTimestamp(timeInMillis)
    add(Calendar.DATE, 7)
    val nextSunday: Date = Converters().fromTimestamp(timeInMillis)
    return Range(lastSunday, nextSunday)
}