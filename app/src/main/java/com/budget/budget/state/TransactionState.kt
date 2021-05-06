package com.budget.budget.state

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.budget.budget.model.Spending

data class TransactionState(val transactions: Async<List<Spending>> = Uninitialized) : MvRxState