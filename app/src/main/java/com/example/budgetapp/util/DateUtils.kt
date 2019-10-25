package com.example.budgetapp.util

import android.util.Range
import com.example.budgetapp.database.Converters
import java.util.*

class DateUtils ***REMOVED***
    companion object ***REMOVED***
        fun findWeekRange(): Range<Date> ***REMOVED***
            val c = Calendar.getInstance()
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
            c.set(Calendar.HOUR_OF_DAY, 0)
            c.set(Calendar.MINUTE, 0)
            c.set(Calendar.SECOND, 0)
            val lastSunday: Date = Converters().fromTimestamp(c.timeInMillis)
            c.add(Calendar.DATE, 7)
            val nextSunday: Date = Converters().fromTimestamp(c.timeInMillis)
            return Range(lastSunday, nextSunday)
***REMOVED***
***REMOVED***
***REMOVED***