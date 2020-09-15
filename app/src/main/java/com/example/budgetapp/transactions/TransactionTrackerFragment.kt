package com.example.budgetapp.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.mvrx.*
import com.example.budgetapp.R
import com.example.budgetapp.components.TransactionTrackerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import timber.log.Timber
import javax.inject.Inject

class TransactionTrackerFragment: BaseMvRxFragment() {

    @Inject
    lateinit var tvmFractory: TransactionViewModel.Factory
    private val tvm: TransactionViewModel by fragmentViewModel(TransactionViewModel::class)

    private lateinit var transactionTrackerAdapter: TransactionTrackerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.transaction_tracker_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transactionTrackerAdapter = TransactionTrackerAdapter()

        view.findViewById<RecyclerView>(R.id.transactionSpendingList).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionTrackerAdapter
            addItemDecoration(DividerItemDecoration(
                context,
                (layoutManager as LinearLayoutManager).layoutDirection)
            )
        }

        view.findViewById<FloatingActionButton>(R.id.uploadTransactionBtn).setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.key_display, TransactionUploadFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun invalidate() {
        withState(tvm) { state ->
            when (state.transactions) {
                is Loading, is Uninitialized -> {}
                is Success -> {
                    transactionTrackerAdapter.submitList(state.transactions.invoke())
                }
                is Fail -> {
                    Timber.e("FAIL")
                }
            }
        }
    }
}