package com.budget.budget.database

import android.content.Context
import androidx.room.Room

class DatabaseManager(val context: Context) {
    private companion object Spender {
        var spendingDb: AppDatabase? = null
    }

    fun getSpendingInfoDb(): AppDatabase {
        var db = spendingDb
        if (db == null) {
            db = Room
                .databaseBuilder(this.context, AppDatabase::class.java, "spending-record.db")
                .build()
        }
        spendingDb = db
        return db
    }
}