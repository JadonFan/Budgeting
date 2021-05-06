package com.budget.budget.ui.transaction

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.budget.budget.R
import com.budget.budget.model.Spending

class GroupedSpendingListAdapter(
    private var context: Context?,
    private var spendingRecord: List<String>,
    private var spendingMap: Map<String, Spending>
) : BaseExpandableListAdapter() {
    private class ViewHolder(view: View) {
        val currItemNameText: TextView = view.findViewById(R.id.currItemNameText)
        val currAmountText: TextView = view.findViewById(R.id.currAmountText)
        val currLocationText: TextView = view.findViewById(R.id.currLocationText)
    }

    override fun getGroup(groupPosition: Int) = spendingRecord[groupPosition]

    override fun isChildSelectable(groupPosition: Int, childPosition: Int) = true

    override fun hasStableIds() = true

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var view = convertView ?: View(context)
        val spendingIdentifier: String = getGroup(groupPosition)
        val amount: Float = spendingMap.getValue(spendingIdentifier).amount
        val location: String = spendingMap.getValue(spendingIdentifier).location

        val inflater = LayoutInflater.from(context)
        if (convertView == null) {
            view = inflater.inflate(R.layout.transaction_item_view, parent, false)
            val holder = ViewHolder(view)
            view.tag = holder
        }

        (view.tag as ViewHolder).apply {
            currItemNameText.text = spendingIdentifier
            currAmountText.text = amount.toString()
            currLocationText.text = location
        }

        return view
    }

    override fun getChild(groupPosition: Int, childPosition: Int) = spendingMap[spendingRecord[groupPosition]]

    override fun getGroupId(groupPosition: Int) = groupPosition.toLong()

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var view = convertView ?: View(context)

        val inflater = LayoutInflater.from(context)
        if (convertView == null) {
            view = inflater.inflate(R.layout.transaction_item_view, parent, false)
        }

        return view
    }

    override fun getChildId(groupPosition: Int, childPosition: Int) = childPosition.toLong()

    override fun getGroupCount() = spendingRecord.size

    override fun getChildrenCount(groupPosition: Int) = 1
}