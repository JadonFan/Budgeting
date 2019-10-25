package com.example.budgetapp.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ExpandableListView
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.budgetapp.R
import com.example.budgetapp.database.DatabaseManager
import com.example.budgetapp.model.SpendingInfo
import com.example.budgetapp.view.GroupedSpendingListAdapter
import com.example.budgetapp.view.RecentSpendingListAdapter
import com.google.android.material.button.MaterialButton
import com.wdullaer.swipeactionadapter.SwipeActionAdapter
import com.wdullaer.swipeactionadapter.SwipeDirection

class TransactionTrackerFragment: Fragment() ***REMOVED***
    companion object ***REMOVED***
        const val SPENDING_DB_NAME = "spending-record.db"
***REMOVED***

    private var transactionLog: ArrayList<SpendingInfo> = ArrayList()


    private class RetrieveTransactionsTask: AsyncTask<Context, Void, Pair<List<String>, HashMap<String, SpendingInfo>>>() ***REMOVED***
        override fun doInBackground(vararg params: Context): Pair<List<String>, HashMap<String, SpendingInfo>> ***REMOVED***
            val spendingRecordDb = DatabaseManager(params[0]).getSpendingInfoDb()
            val spendingRecord = ArrayList(spendingRecordDb!!.spendingInfoDao().getItemName())
            val spendingDetails = ArrayList(spendingRecordDb.spendingInfoDao().getAll())
            val spendingMap = HashMap<String,SpendingInfo>()
            for (index in 0 until spendingRecord.size) ***REMOVED***
                spendingMap[spendingRecord[index]] = spendingDetails[index]
    ***REMOVED***

            return Pair(spendingRecord, spendingMap)
***REMOVED***
***REMOVED***


    private data class RemoveTransactionInfo(val target: SpendingInfo, val ctx: Context)

    private class RemoveTransactionTask: AsyncTask<RemoveTransactionInfo, Void, Void>() ***REMOVED***
        override fun doInBackground(vararg params: RemoveTransactionInfo): Void? ***REMOVED***
            for (param in params) ***REMOVED***
                val spendingRecordDb = DatabaseManager(param.ctx).getSpendingInfoDb()
                spendingRecordDb!!.spendingInfoDao().delete(param.target)
    ***REMOVED***
            return null
***REMOVED***
***REMOVED***

    private fun removeTransaction(recentAdapter: SwipeActionAdapter, position: Int) ***REMOVED***
        val target: SpendingInfo = recentAdapter.getItem(position) as SpendingInfo
        AlertDialog.Builder(context)
            .setTitle(R.string.confirm_delete)
            .setMessage(R.string.delete_transaction_ask)
            .setPositiveButton(R.string.yes) ***REMOVED*** _, _ ->
                activity?.runOnUiThread ***REMOVED***
                    RemoveTransactionTask().execute(RemoveTransactionInfo(target, context as Context))
                    transactionLog.remove(target)
                    recentAdapter.notifyDataSetChanged()
        ***REMOVED***
    ***REMOVED***
            .setNegativeButton(R.string.no) ***REMOVED*** _, _ -> ***REMOVED***
            .create()
            .show()
***REMOVED***


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? ***REMOVED***
        return inflater.inflate(R.layout.spending_tracker_layout, container, false)
***REMOVED***


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) ***REMOVED***
        super.onViewCreated(view, savedInstanceState)

        val spending = RetrieveTransactionsTask().execute(context).get()
        transactionLog.clear()
        for (entry in spending.second) ***REMOVED***
            transactionLog.add(entry.value)
***REMOVED***

        val recentAdapter = SwipeActionAdapter(RecentSpendingListAdapter(context as Context, transactionLog))
        view.findViewById<ListView>(R.id.recentSpendingList).apply ***REMOVED***
            recentAdapter.setListView(this)
            adapter = recentAdapter
***REMOVED***
        recentAdapter.addBackground(SwipeDirection.DIRECTION_FAR_LEFT, R.layout.left_swipe_layout)
            .addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT, R.layout.left_swipe_layout)
            .addBackground(SwipeDirection.DIRECTION_FAR_RIGHT, R.layout.right_swipe_layout)
            .addBackground(SwipeDirection.DIRECTION_NORMAL_RIGHT, R.layout.right_swipe_layout)
        recentAdapter.setSwipeActionListener(object: SwipeActionAdapter.SwipeActionListener ***REMOVED***
            override fun hasActions(position: Int, direction: SwipeDirection?): Boolean ***REMOVED***
                return true
    ***REMOVED***

            override fun shouldDismiss(position: Int, direction: SwipeDirection?): Boolean ***REMOVED***
                return direction != SwipeDirection.DIRECTION_NEUTRAL
    ***REMOVED***

            override fun onSwipe(positions: IntArray?, directions: Array<out SwipeDirection>?) ***REMOVED***
                for (index in positions!!.indices) ***REMOVED***
                    val direction: SwipeDirection? = directions?.get(index)
                    val position: Int = positions[index]
                    when (direction) ***REMOVED***
                        SwipeDirection.DIRECTION_NORMAL_LEFT, SwipeDirection.DIRECTION_FAR_LEFT -> ***REMOVED***
                            removeTransaction(recentAdapter, position)
                ***REMOVED***
                        SwipeDirection.DIRECTION_NORMAL_RIGHT, SwipeDirection.DIRECTION_FAR_RIGHT -> ***REMOVED***
                            removeTransaction(recentAdapter, position)
                ***REMOVED***
                        else -> ***REMOVED******REMOVED***
            ***REMOVED***
                    recentAdapter.notifyDataSetChanged()
        ***REMOVED***
    ***REMOVED***
***REMOVED***)

        val groupedSpendingList = view.findViewById<ExpandableListView>(R.id.groupedSpendingList)
        groupedSpendingList.setAdapter(GroupedSpendingListAdapter(context, spending.first, spending.second))

        view.findViewById<MaterialButton>(R.id.addTransactionBtn).setOnClickListener ***REMOVED***
            fragmentManager?.beginTransaction()
                ?.replace(R.id.keyDisplay, TransactionUploadFragment())
                ?.addToBackStack(null)
                ?.commit()
***REMOVED***
***REMOVED***
***REMOVED***