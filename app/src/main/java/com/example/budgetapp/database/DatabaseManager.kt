package com.example.budgetapp.database

import android.content.Context
import androidx.room.Room
import com.example.budgetapp.fragment.TransactionTrackerFragment

class DatabaseManager(val context: Context) {
    private companion object Spender {
        var spendingInfoDb: AppDatabase? = null
    }

    fun getSpendingInfoDb(): AppDatabase? {
        if (spendingInfoDb == null) {
            spendingInfoDb = Room.databaseBuilder(this.context, AppDatabase::class.java,
                TransactionTrackerFragment.SPENDING_DB_NAME).build()
        }
        return spendingInfoDb
    }
}