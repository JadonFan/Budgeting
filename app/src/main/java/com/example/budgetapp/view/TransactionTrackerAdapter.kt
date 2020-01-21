package com.example.budgetapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetapp.R
import com.example.budgetapp.model.SpendingInfo

class TransactionTrackerAdapter: ListAdapter<SpendingInfo, RecyclerView.ViewHolder>(DiffCallback()) {
    companion object {
        class DiffCallback: DiffUtil.ItemCallback<SpendingInfo>() {
            override fun areItemsTheSame(oldItem: SpendingInfo, newItem: SpendingInfo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SpendingInfo, newItem: SpendingInfo): Boolean {
                return oldItem == newItem
            }
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
        val spendingInfo: SpendingInfo? = getItem(position)
        val vh0: ViewHolder0 = holder as ViewHolder0

        vh0.transactionNameLabel.apply {
            text = spendingInfo?.itemName
        }
        vh0.transactionAmountLabel.apply {
            text = String.format(context.getString(R.string.dollar_money_format), spendingInfo?.amount)
        }
        vh0.transactionLocationLabel.apply {
            text = spendingInfo?.location
        }
    }
}