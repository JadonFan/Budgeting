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
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class ReportFragment: Fragment(), CoroutineScope {
    private var currSpendAmount = 0f
    private var targetSpendAmount = 500f


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

        val tf: Typeface = Typeface.createFromAsset(activity?.assets, "Roboto-Regular.ttf")

        launch {
            val weekRange = DateUtils.findWeekRange()
            withContext(Dispatchers.IO) {
                // TODO use repo class
                currSpendAmount = DatabaseManager(context!!).getSpendingInfoDb()!!.spendingInfoDao().findWeeklySpending(weekRange.lower, weekRange.upper)
                activity?.runOnUiThread {
                    view.findViewById<CircularProgressBar>(R.id.weeklySpendingProgress).apply {
                        progress = currSpendAmount / targetSpendAmount
                    }
                    view.findViewById<TextView>(R.id.currWeeklyText).apply {
                        typeface = tf
                        text = HtmlCompat.fromHtml(
                            String.format("Current: <b>$%.2f</b>", currSpendAmount),
                            HtmlCompat.FROM_HTML_MODE_LEGACY)
                    }
                }
            }
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

        if (savedInstanceState == null) {
            updateWeeklyData()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


    /**
     * TODO use an AlarmManager to schedule the update every 7 days
     * Intentionally does not work as permissions are set such that READ and WRITE operations are both currently disabled
      */
    private fun updateWeeklyData() {
        val weeklyData = hashMapOf(
            "week" to DateUtils.findWeekRange(),
            "year" to GregorianCalendar.YEAR,
            "spending" to currSpendAmount,
            "target" to targetSpendAmount
        )
        val db = FirebaseFirestore.getInstance()
        db.collection("weekly_data")
            .document("${GregorianCalendar.YEAR}${DateUtils.findWeekRange()}")
            .set(weeklyData)
            .addOnSuccessListener {
                println("SUCCESS")
            }
            .addOnFailureListener {
                println("FAILED")
            }
    }
}