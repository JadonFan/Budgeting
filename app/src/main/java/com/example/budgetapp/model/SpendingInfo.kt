package com.example.budgetapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class SpendingInfo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "itemName") var itemName: String,
    @ColumnInfo(name = "amount") var amount: Float,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "location") var location: String,
    @ColumnInfo(name = "purchaseTime") var purchaseTime: Date,
    @ColumnInfo(name = "payment_method") var paymentMethod: String
)