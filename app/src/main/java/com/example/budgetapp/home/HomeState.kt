package com.example.budgetapp.home

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized

data class HomeState(val currSpendingAmount: Async<Float> = Uninitialized) : MvRxState