package com.example.budgetapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.budgetapp.database.DatabaseManager
import com.example.budgetapp.model.SpendingInfo

class TransactionViewModel(val app: Application) : AndroidViewModel(app) {
    private val transactions: MutableLiveData<List<SpendingInfo>> by lazy {
        MutableLiveData<List<SpendingInfo>>().also {
            loadTransactions()
        }
    }

    fun getTransactions(): LiveData<List<SpendingInfo>> {
        return transactions
    }

    private fun loadTransactions(): LiveData<List<SpendingInfo>> {
        return DatabaseManager(app.applicationContext).getSpendingInfoDb()!!.spendingInfoDao().getAll()
    }
}