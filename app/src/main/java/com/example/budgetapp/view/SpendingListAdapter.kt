package com.example.budgetapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.budgetapp.R
import com.example.budgetapp.model.SpendingInfo

class SpendingListAdapter(var context: Context?, private var spendingRecord: List<String>,
                          var spendingMap: Map<String, SpendingInfo>)
    : BaseExpandableListAdapter() {

    override fun getGroup(groupPosition: Int): Any {
        return spendingRecord[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var view = convertView ?: View(context)
        val spendingIdentifier: String = getGroup(groupPosition) as String
        val amount: Double = spendingMap.getValue(spendingIdentifier).amount
        val location: String = spendingMap.getValue(spendingIdentifier).location

        if (convertView == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.tracker_group, parent, false)
        }
        view.findViewById<TextView>(R.id.spending_identifier_text).apply {
            text = spendingIdentifier
        }
        view.findViewById<TextView>(R.id.spending_amount_text).apply {
            text = amount.toString()
        }
        view.findViewById<TextView>(R.id.spending_location_text).apply {
            text = location
        }

        return view
    }

    override fun getChild(groupPosition: Int, childPosition: Int): SpendingInfo? {
        return spendingMap[spendingRecord[groupPosition]]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var view = convertView ?: View(context)

        if (convertView == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.tracker_group, parent, false)
        }

        return view
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return spendingRecord.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return 1
    }
}