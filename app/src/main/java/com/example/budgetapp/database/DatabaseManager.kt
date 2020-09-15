package com.example.budgetapp.database

import android.content.Context
import androidx.room.Room

class DatabaseManager(val context: Context) {
    private companion object Spender {
        var spendingDb: AppDatabase? = null
    }

    fun getSpendingInfoDb(): AppDatabase {
        if (spendingDb == null) {
            spendingDb = Room
                .databaseBuilder(this.context, AppDatabase::class.java, "spending-record.db")
                .fallbackToDestructiveMigration()
                .build()
        }
        return spendingDb!!
    }
}