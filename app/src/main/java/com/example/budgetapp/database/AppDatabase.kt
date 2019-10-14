package com.example.budgetapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.budgetapp.model.SpendingInfo

@Database(entities = [SpendingInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun spendingInfoDao(): SpendingInfoDao
}