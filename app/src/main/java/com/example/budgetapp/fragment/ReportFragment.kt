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

class ReportFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.spending_report_layout, container, false)
    }

    private class FindWeeklySpendingTask: AsyncTask<Context, Void, Float>() {
        override fun doInBackground(vararg params: Context?): Float {
            val weekRange: Range<Date> = DateUtils.findWeekRange()
            return DatabaseManager(params[0] as Context).getSpendingInfoDb()!!.spendingInfoDao()
                .findWeeklySpending(weekRange.lower, weekRange.upper)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currSpendAmount = FindWeeklySpendingTask().execute(context).get()
        val targetSpendAmount = 500f

        val tf: Typeface = Typeface.createFromAsset(activity?.assets, "Roboto-Regular.ttf")
        view.findViewById<TextView>(R.id.currWeeklyText).apply {
            typeface = tf
            text = HtmlCompat.fromHtml(
                String.format("Current: <b>$%.2f</b>", currSpendAmount),
                HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
        view.findViewById<TextView>(R.id.currTargetText).apply {
            typeface = tf
            text = HtmlCompat.fromHtml(
                String.format("Target: <b>$%.2f</b>", targetSpendAmount),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }

        val spendingProgress = view.findViewById<CircularProgressBar>(R.id.weeklySpendingProgress)
        spendingProgress.progress = currSpendAmount / targetSpendAmount
    }
}