package com.example.budgetapp.home

import com.airbnb.mvrx.*
import com.example.budgetapp.database.DatabaseManager
import com.example.budgetapp.util.DateUtils
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.schedulers.Schedulers

class HomeViewModel @AssistedInject constructor(
    @Assisted initialState: HomeState,
    @Assisted private val homeRepository: HomeRepository
) :
    BaseMvRxViewModel<HomeState>(initialState, debugMode = BuildConfig.DEBUG) {

    init {
        getCurrSpendingAmount()
    }

    private fun getCurrSpendingAmount() = withState { state ->
        if (state.currSpendingAmount is Loading) return@withState

        val weekRange = DateUtils.findWeekRange()
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
        override fun create(viewModelContext: ViewModelContext,
                            state: HomeState
        ): HomeViewModel? {
            return HomeViewModel(
                state,
                HomeRepository(DatabaseManager(viewModelContext.activity).getSpendingInfoDb())
            )
        }
    }
}