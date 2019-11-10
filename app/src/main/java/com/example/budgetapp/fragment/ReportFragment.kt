package com.example.budgetapp.fragment

import android.content.Context
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.util.Range
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.example.budgetapp.R
import com.example.budgetapp.database.DatabaseManager
import com.example.budgetapp.util.DateUtils
import com.example.budgetapp.view.CircularProgressBar
import java.util.*

class ReportFragment: Fragment() ***REMOVED***
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? ***REMOVED***
        return inflater.inflate(R.layout.spending_report_layout, container, false)
***REMOVED***


    private class FindWeeklySpendingTask: AsyncTask<Context, Void, Float>() ***REMOVED***
        override fun doInBackground(vararg params: Context?): Float ***REMOVED***
            val weekRange: Range<Date> = DateUtils.findWeekRange()
            return DatabaseManager(params[0] as Context).getSpendingInfoDb()!!.spendingInfoDao()
                .findWeeklySpending(weekRange.lower, weekRange.upper)
***REMOVED***
***REMOVED***


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) ***REMOVED***
        super.onViewCreated(view, savedInstanceState)
        val currSpendAmount = FindWeeklySpendingTask().execute(context).get()
        val targetSpendAmount = 500f

        val tf: Typeface = Typeface.createFromAsset(activity?.assets, "Roboto-Regular.ttf")
        view.findViewById<TextView>(R.id.currWeeklyText).apply ***REMOVED***
            typeface = tf
            text = HtmlCompat.fromHtml(
                String.format("Current: <b>$%.2f</b>", currSpendAmount),
                HtmlCompat.FROM_HTML_MODE_LEGACY)
***REMOVED***
        view.findViewById<TextView>(R.id.currTargetText).apply ***REMOVED***
            typeface = tf
            text = HtmlCompat.fromHtml(
                String.format("Target: <b>$%.2f</b>", targetSpendAmount),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
***REMOVED***
        val cal: Calendar = GregorianCalendar.getInstance()
        view.findViewById<TextView>(R.id.weekText).apply ***REMOVED***
            typeface = tf
            text = String.format(getString(R.string.week_order), cal.get(Calendar.WEEK_OF_YEAR))
***REMOVED***

        val spendingProgress = view.findViewById<CircularProgressBar>(R.id.weeklySpendingProgress)
        spendingProgress.progress = currSpendAmount / targetSpendAmount
***REMOVED***
***REMOVED***