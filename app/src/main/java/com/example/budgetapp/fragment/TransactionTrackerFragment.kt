package com.example.budgetapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetapp.R
import com.example.budgetapp.view.TransactionTrackerAdapter
import com.example.budgetapp.viewmodel.TransactionViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TransactionTrackerFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.spending_tracker_layout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val tvm: TransactionViewModel by lazy {
            ViewModelProviders.of(this).get(TransactionViewModel::class.java)
        }
        val transactionTrackerAdapter = TransactionTrackerAdapter()
        tvm.getTransactions()!!.observe(viewLifecycleOwner, Observer {
            transactionTrackerAdapter.submitList(it)
        })

        view.findViewById<RecyclerView>(R.id.transactionSpendingList).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionTrackerAdapter
            addItemDecoration(DividerItemDecoration(context, (layoutManager as LinearLayoutManager).layoutDirection))
        }

        view.findViewById<FloatingActionButton>(R.id.uploadTransactionBtn).setOnClickListener {
            fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.key_display, TransactionUploadFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}