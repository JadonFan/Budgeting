package com.example.budgetapp.transactions

import com.airbnb.mvrx.*
import com.example.budgetapp.database.DatabaseManager
import com.example.budgetapp.home.HomeState
import com.example.budgetapp.home.HomeViewModel
import com.example.budgetapp.models.Spending
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.schedulers.Schedulers
import java.util.*

class TransactionViewModel @AssistedInject constructor(
    @Assisted initialState: TransactionState,
    @Assisted private val transactionRepository: TransactionRepository
) : BaseMvRxViewModel<TransactionState>(initialState, debugMode = BuildConfig.DEBUG) {

    init {
        getTransactions()
    }

    private fun getTransactions() = withState { state ->
        if (state.transactions is Loading) return@withState

        transactionRepository.getTransactions()
            .subscribeOn(Schedulers.io())
            .execute { copy(transactions = it) }
    }

    fun addNewTransaction(itemName: String, amount: Float, location: String) {
        val spending = Spending(
            0,
            itemName,
            amount,
            "",
            location,
            Date(),
            ""
        )
        transactionRepository.addNewTransaction(spending)
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(state: HomeState, transactionRepository: TransactionRepository): HomeViewModel
    }

    companion object : MvRxViewModelFactory<TransactionViewModel, TransactionState> {
        override fun create(viewModelContext: ViewModelContext,
                            state: TransactionState): TransactionViewModel? {
            return TransactionViewModel(
                state,
                TransactionRepository(DatabaseManager(viewModelContext.activity).getSpendingInfoDb())
            )
        }
    }
}