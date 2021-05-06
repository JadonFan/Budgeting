package com.budget.budget.viewmodel

import com.airbnb.mvrx.*
import com.budget.budget.database.DatabaseManager
import com.budget.budget.state.HomeState
import com.budget.budget.repository.HomeRepository
import com.budget.budget.util.findWeekRange
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.schedulers.Schedulers
import java.util.*

class HomeViewModel @AssistedInject constructor(
    @Assisted initialState: HomeState,
    @Assisted private val homeRepository: HomeRepository
) : BaseMvRxViewModel<HomeState>(initialState, debugMode = BuildConfig.DEBUG) {

    init {
        getCurrSpendingAmount()
    }

    private fun getCurrSpendingAmount() = withState { state ->
        if (state.currSpendingAmount is Loading) return@withState

        val weekRange = Calendar.getInstance().findWeekRange()
        homeRepository.getCurrSpendingAmount(weekRange.lower, weekRange.upper)
            .subscribeOn(Schedulers.io())
            .execute { copy(currSpendingAmount = it) }
    }

    fun updateWeeklySpending(currSpendAmount: Float, targetSpendAmount: Float) =
        homeRepository.updateWeeklySpending(currSpendAmount, targetSpendAmount)

    @AssistedInject.Factory
    interface Factory {
        fun create(state: HomeState, homeRepository: HomeRepository): HomeViewModel
    }

    companion object : MvRxViewModelFactory<HomeViewModel, HomeState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: HomeState
        ) = HomeViewModel(
            state,
            HomeRepository(DatabaseManager(viewModelContext.activity).getSpendingInfoDb())
        )
    }
}