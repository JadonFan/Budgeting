package com.example.budgetapp.database

import android.content.Context
import androidx.room.Room
import com.example.budgetapp.fragment.TransactionTrackerFragment

class DatabaseManager(val context: Context) ***REMOVED***
    private companion object Spender ***REMOVED***
        var spendingInfoDb: AppDatabase? = null
***REMOVED***

    fun getSpendingInfoDb(): AppDatabase? ***REMOVED***
        if (spendingInfoDb == null) ***REMOVED***
            spendingInfoDb = Room.databaseBuilder(this.context, AppDatabase::class.java,
                TransactionTrackerFragment.SPENDING_DB_NAME).build()
***REMOVED***
        return spendingInfoDb
***REMOVED***
***REMOVED***