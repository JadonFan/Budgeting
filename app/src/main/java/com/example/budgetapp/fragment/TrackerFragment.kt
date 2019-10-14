package com.example.budgetapp.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.budgetapp.R
import com.example.budgetapp.database.AppDatabase
import com.example.budgetapp.model.SpendingInfo
import com.example.budgetapp.view.SpendingListAdapter
import com.google.android.material.button.MaterialButton

class TrackerFragment: Fragment() ***REMOVED***
    companion object ***REMOVED***
        private const val SPENDING_DB_NAME = "spending-record.db"
***REMOVED***

    private class RetrieveSpendingRecordTask: AsyncTask<Context, Void, Pair<List<String>, HashMap<String, SpendingInfo>>>() ***REMOVED***
        override fun doInBackground(vararg params: Context?): Pair<List<String>, HashMap<String, SpendingInfo>> ***REMOVED***
            val spendingRecordDb = Room.databaseBuilder(params[0] as Context, AppDatabase::class.java,
                SPENDING_DB_NAME).build()
            val spendingRecord = ArrayList(spendingRecordDb.spendingInfoDao().getIdentifiers())
            val spendingDetails = ArrayList(spendingRecordDb.spendingInfoDao().getAll())
            val spendingMap = HashMap<String,SpendingInfo>()
            for (index in 0 until spendingRecord.size) ***REMOVED***
                spendingMap[spendingRecord[index]] = spendingDetails[index]
    ***REMOVED***

            return Pair(spendingRecord, spendingMap)
***REMOVED***
***REMOVED***

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? ***REMOVED***
        return inflater.inflate(R.layout.spending_tracker_layout, container, false)
***REMOVED***

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) ***REMOVED***
        super.onViewCreated(view, savedInstanceState)

        val spending = RetrieveSpendingRecordTask().execute(context).get()
        val recentSpendingList = view.findViewById<ExpandableListView>(R.id.recentSpendingList)
        recentSpendingList.setAdapter(SpendingListAdapter(context, spending.first, spending.second))

        view.findViewById<MaterialButton>(R.id.addTransactionBtn).setOnClickListener ***REMOVED***
            Toast.makeText(activity, "TESTING...", Toast.LENGTH_SHORT).show()
            val ft = fragmentManager?.beginTransaction()
            ft?.replace(R.id.keyDisplay, TransactionUploadFragment())
            ft?.commit()
***REMOVED***
***REMOVED***

    fun findLocation(view: View) ***REMOVED***

***REMOVED***
***REMOVED***