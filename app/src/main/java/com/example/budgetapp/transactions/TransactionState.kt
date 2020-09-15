package com.example.budgetapp.transactions

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.example.budgetapp.models.Spending

data class TransactionState(val transactions: Async<List<Spending>> = Uninitialized) : MvRxState