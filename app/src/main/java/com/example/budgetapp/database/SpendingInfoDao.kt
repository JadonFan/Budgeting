package com.example.budgetapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.budgetapp.model.SpendingInfo
import java.util.*

@Dao
interface SpendingInfoDao ***REMOVED***
    @Query("SELECT * FROM spendinginfo")
    fun getAll(): List<SpendingInfo>

    @Query("SELECT itemName FROM spendinginfo")
    fun getItemName(): List<String>

    @Query("SELECT * FROM spendinginfo WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<SpendingInfo>

    @Query("SELECT SUM(amount) FROM spendinginfo WHERE purchaseTime BETWEEN :from AND :to ")
    fun findWeeklySpending(from: Date, to: Date): Float

    @Insert
    fun insertAll(vararg spendingInfo: SpendingInfo)

    @Delete
    fun delete(spendingInfo: SpendingInfo)
***REMOVED***