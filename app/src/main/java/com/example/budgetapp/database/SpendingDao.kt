package com.example.budgetapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.budgetapp.models.Spending
import io.reactivex.Observable
import java.util.*

@Dao
interface SpendingDao {
    @Query("SELECT * FROM spending")
    fun getAll(): Observable<List<Spending>>

    @Query("SELECT itemName FROM spending")
    fun getItemName(): List<String>

    @Query("SELECT * FROM spending WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<Spending>

    @Query("SELECT SUM(amount) FROM spending WHERE purchaseTime BETWEEN :from AND :to ")
    fun findWeeklySpending(from: Date, to: Date): Observable<Float>

    @Insert
    fun insertAll(vararg spending: Spending)

    @Delete
    fun delete(spending: Spending)
}