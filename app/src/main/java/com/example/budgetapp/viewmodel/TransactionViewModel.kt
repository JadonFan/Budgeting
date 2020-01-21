package com.example.budgetapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.budgetapp.database.DatabaseManager
import com.example.budgetapp.model.SpendingInfo

// TODO load in repository class
class TransactionViewModel(val app: Application) : AndroidViewModel(app) {
    private var liveTransactions: LiveData<List<SpendingInfo>>? = null
    
    fun getTransactions(): LiveData<List<SpendingInfo>>? {
        if (liveTransactions == null) {
            liveTransactions = DatabaseManager(app.applicationContext).getSpendingInfoDb()!!.spendingInfoDao().getAll()
        }
        return liveTransactions
    }
}