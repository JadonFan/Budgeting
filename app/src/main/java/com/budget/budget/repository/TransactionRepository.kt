package com.budget.budget.repository

import com.budget.budget.database.AppDatabase
import com.budget.budget.model.Spending
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val db: AppDatabase,
) {

    fun getTransactions() = db.spendingDao().getAll()

    suspend fun addNewTransaction(spending: Spending) {
        db.spendingDao().insertAll(spending)
    }
}