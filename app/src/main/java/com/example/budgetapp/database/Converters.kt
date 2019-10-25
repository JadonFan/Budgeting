package com.example.budgetapp.database

import androidx.room.TypeConverter
import java.util.*

class Converters ***REMOVED***
    @TypeConverter
    fun fromTimestamp(value: Long): Date ***REMOVED***
        return Date(value)
***REMOVED***

    @TypeConverter
    fun dateToTimestamp(date: Date): Long ***REMOVED***
        return date.time
***REMOVED***
***REMOVED***