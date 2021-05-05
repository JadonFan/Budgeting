package com.example.budgetapp.transactions

import com.example.budgetapp.database.AppDatabase
import com.example.budgetapp.models.Spending
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val db: AppDatabase,
) {

    fun getTransactions() = db.spendingDao().getAll()

    suspend fun addNewTransaction(spending: Spending) {
        db.spendingDao().insertAll(spending)
    }
}