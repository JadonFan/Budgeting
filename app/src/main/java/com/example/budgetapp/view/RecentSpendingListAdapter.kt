package com.example.budgetapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.budgetapp.R
import com.example.budgetapp.model.SpendingInfo

class RecentSpendingListAdapter(val ctx: Context, records: ArrayList<SpendingInfo>):
    ArrayAdapter<SpendingInfo>(ctx, 0, records) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val info: SpendingInfo? = getItem(position)
        var view = convertView ?: View(context)
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.transaction_item_view, parent, false)
        }

        view.findViewById<TextView>(R.id.currItemNameText)?.apply {
            text = info!!.itemName
        }
        view.findViewById<TextView>(R.id.currAmountText)?.apply {
            text = String.format(ctx.getString(R.string.dollar_money_format), info!!.amount)
        }
        view.findViewById<TextView>(R.id.currLocationText)?.apply {
            text = info!!.location
        }

        return view
    }
}