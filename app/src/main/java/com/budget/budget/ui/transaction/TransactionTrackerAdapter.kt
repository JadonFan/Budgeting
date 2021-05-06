package com.budget.budget.ui.transaction

import android.content.Context
import android.icu.util.Currency
import android.icu.util.CurrencyAmount
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.budget.budget.R
import com.budget.budget.model.Spending
import com.budget.budget.util.asCurrencyAmount
import java.util.*

class TransactionTrackerAdapter: ListAdapter<Spending, RecyclerView.ViewHolder>(DiffCallback()) {
    companion object {
        class DiffCallback: DiffUtil.ItemCallback<Spending>() {
            override fun areItemsTheSame(
                oldItem: Spending,
                newItem: Spending
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Spending,
                newItem: Spending
            ) = oldItem == newItem
        }
    }

    private class ViewHolder0(convertView: View): RecyclerView.ViewHolder(convertView) {
        val transactionNameLabel: TextView = convertView.findViewById(R.id.currItemNameText)
        val transactionAmountLabel: TextView = convertView.findViewById(R.id.currAmountText)
        val transactionLocationLabel: TextView = convertView.findViewById(R.id.currLocationText)
    }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val v: View = LayoutInflater.from(context).inflate(R.layout.transaction_item_view, parent, false)
        return ViewHolder0(v)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val spending: Spending? = getItem(position)
        (holder as ViewHolder0).apply {
            transactionNameLabel.text = spending?.itemName
            transactionAmountLabel.text = spending?.amount.asCurrencyAmount()
            transactionLocationLabel.text = spending?.location
        }
    }
}