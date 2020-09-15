package com.example.budgetapp.transactions

import com.example.budgetapp.database.AppDatabase
import com.example.budgetapp.models.Spending
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val db: AppDatabase,
) {

    private var job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Main)

    fun getTransactions() = db.spendingDao().getAll()

    fun addNewTransaction(spending: Spending) {
        Thread {
            db.spendingDao().insertAll(spending)
        }.start()
    }
}