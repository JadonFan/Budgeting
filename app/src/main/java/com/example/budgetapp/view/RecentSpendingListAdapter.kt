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
    ArrayAdapter<SpendingInfo>(ctx, 0, records) ***REMOVED***
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? ***REMOVED***
        val info: SpendingInfo? = getItem(position)
        var view = convertView ?: View(context)
        if (convertView == null) ***REMOVED***
            view = LayoutInflater.from(context).inflate(R.layout.transaction_item_view, parent, false)
***REMOVED***

        view.findViewById<TextView>(R.id.currItemNameText)?.apply ***REMOVED***
            text = info!!.itemName
***REMOVED***
        view.findViewById<TextView>(R.id.currAmountText)?.apply ***REMOVED***
            text = String.format(ctx.getString(R.string.dollar_money_format), info!!.amount)
***REMOVED***
        view.findViewById<TextView>(R.id.currLocationText)?.apply ***REMOVED***
            text = info!!.location
***REMOVED***

        return view
***REMOVED***
***REMOVED***