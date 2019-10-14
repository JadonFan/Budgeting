package com.example.budgetapp.model

import android.icu.util.Calendar
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time

@Entity
data class SpendingInfo(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "identifier") var identifier: String,
    @ColumnInfo(name = "amount") var amount: Double,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "location") var location: String,
    @ColumnInfo(name = "time") var time: String,
    @ColumnInfo(name ="payment_method") var paymentMethod: String
)