package com.budget.budget.state

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized

data class HomeState(val currSpendingAmount: Async<Float> = Uninitialized) : MvRxState