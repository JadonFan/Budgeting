package com.example.budgetapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.budgetapp.model.SpendingInfo

@Dao
interface SpendingInfoDao ***REMOVED***
    @Query("SELECT * FROM spendinginfo")
    fun getAll(): List<SpendingInfo>

    @Query("SELECT identifier FROM spendinginfo")
    fun getIdentifiers(): List<String>

    @Query("SELECT * FROM spendinginfo WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<SpendingInfo>

    @Insert
    fun insertAll(vararg spendingInfo: SpendingInfo)

    @Delete
    fun delete(spendingInfo: SpendingInfo)
***REMOVED***