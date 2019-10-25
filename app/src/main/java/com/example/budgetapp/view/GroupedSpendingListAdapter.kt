package com.example.budgetapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.budgetapp.R
import com.example.budgetapp.model.SpendingInfo

class GroupedSpendingListAdapter(private var context: Context?, private var spendingRecord: List<String>,
                                 private var spendingMap: Map<String, SpendingInfo>)
    : BaseExpandableListAdapter() ***REMOVED***

    override fun getGroup(groupPosition: Int): Any ***REMOVED***
        return spendingRecord[groupPosition]
***REMOVED***

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean ***REMOVED***
        return true
***REMOVED***

    override fun hasStableIds(): Boolean ***REMOVED***
        return true
***REMOVED***

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View ***REMOVED***
        var view = convertView ?: View(context)
        val spendingIdentifier: String = getGroup(groupPosition) as String
        val amount: Float = spendingMap.getValue(spendingIdentifier).amount
        val location: String = spendingMap.getValue(spendingIdentifier).location

        if (convertView == null) ***REMOVED***
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.transaction_item_view, parent, false)
***REMOVED***
        view.findViewById<TextView>(R.id.currItemNameText).apply ***REMOVED***
            text = spendingIdentifier
***REMOVED***
        view.findViewById<TextView>(R.id.currAmountText).apply ***REMOVED***
            text = amount.toString()
***REMOVED***
        view.findViewById<TextView>(R.id.currLocationText).apply ***REMOVED***
            text = location
***REMOVED***

        return view
***REMOVED***

    override fun getChild(groupPosition: Int, childPosition: Int): SpendingInfo? ***REMOVED***
        return spendingMap[spendingRecord[groupPosition]]
***REMOVED***

    override fun getGroupId(groupPosition: Int): Long ***REMOVED***
        return groupPosition.toLong()
***REMOVED***

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View ***REMOVED***
        var view = convertView ?: View(context)

        if (convertView == null) ***REMOVED***
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.transaction_item_view, parent, false)
***REMOVED***

        return view
***REMOVED***

    override fun getChildId(groupPosition: Int, childPosition: Int): Long ***REMOVED***
        return childPosition.toLong()
***REMOVED***

    override fun getGroupCount(): Int ***REMOVED***
        return spendingRecord.size
***REMOVED***

    override fun getChildrenCount(groupPosition: Int): Int ***REMOVED***
        return 1
***REMOVED***
***REMOVED***