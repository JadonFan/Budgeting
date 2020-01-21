package com.example.budgetapp.fragment

import android.graphics.Typeface
import android.os.Bundle
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
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class ReportFragment: Fragment(), CoroutineScope {
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.spending_report_layout, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var currSpendAmount = 0f
        launch {
            val weekRange = DateUtils.findWeekRange()
            withContext(Dispatchers.IO) {
                currSpendAmount = DatabaseManager(context!!).getSpendingInfoDb()!!.spendingInfoDao().findWeeklySpending(weekRange.lower, weekRange.upper)
            }
        }
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
        val cal: Calendar = GregorianCalendar.getInstance()
        view.findViewById<TextView>(R.id.weekText).apply {
            typeface = tf
            text = String.format(getString(R.string.week_order), cal.get(Calendar.WEEK_OF_YEAR))
        }

        val spendingProgress = view.findViewById<CircularProgressBar>(R.id.weeklySpendingProgress)
        spendingProgress.progress = currSpendAmount / targetSpendAmount
    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}