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
import com.example.budgetapp.view.CircularProgressBar

class ReportFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.spending_report_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var currSpendAmount = 100f
        var targetSpendAmount = 500f

        val tf: Typeface = Typeface.createFromAsset(activity?.assets, "Roboto-Regular.ttf")
        view.findViewById<TextView>(R.id.curr_spending).apply {
            typeface = tf
            text = HtmlCompat.fromHtml(
                String.format("Current: <b>$%.2f</b>", currSpendAmount),
                HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
        view.findViewById<TextView>(R.id.target_spending).apply {
            typeface = tf
            text = HtmlCompat.fromHtml(
                String.format("Target: <b>$%.2f</b>", targetSpendAmount),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }

        val spendingProgress = view.findViewById<CircularProgressBar>(R.id.weekly_spending_progress)
        spendingProgress.progress = currSpendAmount / targetSpendAmount
    }
}