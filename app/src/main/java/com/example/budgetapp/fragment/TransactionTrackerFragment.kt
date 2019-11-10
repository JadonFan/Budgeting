package com.example.budgetapp.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.budgetapp.R
import com.example.budgetapp.database.DatabaseManager
import com.example.budgetapp.model.SpendingInfo
import com.example.budgetapp.view.GroupedSpendingListAdapter
import com.example.budgetapp.view.RecentSpendingListAdapter
import com.google.android.material.button.MaterialButton
import com.wdullaer.swipeactionadapter.SwipeActionAdapter
import com.wdullaer.swipeactionadapter.SwipeDirection
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class TransactionTrackerFragment: Fragment() {
    companion object {
        const val SPENDING_DB_NAME = "spending-record.db"
    }

    private var transactionLog: ArrayList<SpendingInfo> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.spending_tracker_layout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spending = RetrieveTransactionsTask().execute(context).get()
        transactionLog.clear()
        for (entry in spending.second) {
            transactionLog.add(entry.value)
        }

        val recentAdapter = SwipeActionAdapter(RecentSpendingListAdapter(context as Context, transactionLog))
        view.findViewById<ListView>(R.id.recentSpendingList).apply {
            recentAdapter.setListView(this)
            adapter = recentAdapter
            emptyView = view.findViewById(R.id.emptyTrackText)
        }
        recentAdapter.addBackground(SwipeDirection.DIRECTION_FAR_LEFT, R.layout.left_swipe_layout)
            .addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT, R.layout.left_swipe_layout)
            .addBackground(SwipeDirection.DIRECTION_FAR_RIGHT, R.layout.right_swipe_layout)
            .addBackground(SwipeDirection.DIRECTION_NORMAL_RIGHT, R.layout.right_swipe_layout)
        recentAdapter.setSwipeActionListener(object: SwipeActionAdapter.SwipeActionListener {
            override fun hasActions(position: Int, direction: SwipeDirection?): Boolean {
                return true
            }

            override fun shouldDismiss(position: Int, direction: SwipeDirection?): Boolean {
                return direction != SwipeDirection.DIRECTION_NEUTRAL
            }

            override fun onSwipe(positions: IntArray?, directions: Array<out SwipeDirection>?) {
                for (index in positions!!.indices) {
                    val direction: SwipeDirection? = directions?.get(index)
                    val position: Int = positions[index]
                    when (direction) {
                        SwipeDirection.DIRECTION_NORMAL_LEFT, SwipeDirection.DIRECTION_FAR_LEFT -> {
                            removeTransaction(recentAdapter, position)
                        }
                        SwipeDirection.DIRECTION_NORMAL_RIGHT, SwipeDirection.DIRECTION_FAR_RIGHT -> {
                            removeTransaction(recentAdapter, position)
                        }
                        else -> {}
                    }
                    recentAdapter.notifyDataSetChanged()
                }
            }
        })

        val groupedSpendingList = view.findViewById<ExpandableListView>(R.id.groupedSpendingList)
        groupedSpendingList.setAdapter(GroupedSpendingListAdapter(context, spending.first, spending.second))

        view.findViewById<MaterialButton>(R.id.addTransactionBtn).setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.keyDisplay, TransactionUploadFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
    }


    private class RetrieveTransactionsTask: AsyncTask<Context, Void, Pair<List<String>, HashMap<String, SpendingInfo>>>() {
        override fun doInBackground(vararg params: Context): Pair<List<String>, HashMap<String, SpendingInfo>> {
            val spendingRecordDb = DatabaseManager(params[0]).getSpendingInfoDb()
            val spendingRecord = ArrayList(spendingRecordDb!!.spendingInfoDao().getItemName())
            val spendingDetails = ArrayList(spendingRecordDb.spendingInfoDao().getAll())
            val spendingMap = HashMap<String,SpendingInfo>()
            for (index in 0 until spendingRecord.size) {
                spendingMap[spendingRecord[index]] = spendingDetails[index]
            }

            return Pair(spendingRecord, spendingMap)
        }
    }


    private data class RemoveTransactionInfo(val target: SpendingInfo, val ctx: Context)

    private class RemoveTransactionTask: AsyncTask<RemoveTransactionInfo, Void, Void>() {
        override fun doInBackground(vararg params: RemoveTransactionInfo): Void? {
            for (param in params) {
                val spendingRecordDb = DatabaseManager(param.ctx).getSpendingInfoDb()
                spendingRecordDb!!.spendingInfoDao().delete(param.target)
            }
            return null
        }
    }


    private fun removeTransaction(recentAdapter: SwipeActionAdapter, position: Int) {
        val target: SpendingInfo = recentAdapter.getItem(position) as SpendingInfo
        AlertDialog.Builder(context)
            .setTitle(R.string.confirm_delete)
            .setMessage(R.string.delete_transaction_ask)
            .setPositiveButton(android.R.string.yes) { _, _ ->
                activity?.runOnUiThread {
                    RemoveTransactionTask().execute(RemoveTransactionInfo(target, context as Context))
                    transactionLog.remove(target)
                    recentAdapter.notifyDataSetChanged()
                }
            }
            .setNegativeButton(android.R.string.no) { _, _ -> }
            .create()
            .show()
    }
}