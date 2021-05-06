package com.budget.budget.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.airbnb.mvrx.*
import com.budget.budget.R
import com.budget.budget.ui.common.CircularProgressBar
import com.budget.budget.util.asCurrencyAmount
import com.budget.budget.viewmodel.HomeViewModel
import java.util.*
import javax.inject.Inject

class HomeFragment: BaseMvRxFragment() {
    private var currSpendAmount = 0f
    private var targetSpendAmount = 500f

    @Inject
    lateinit var hvmFactory: HomeViewModel.Factory
    private val hvm: HomeViewModel by fragmentViewModel(HomeViewModel::class)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.home_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.currTargetText).apply {
            text = HtmlCompat.fromHtml(
                getString(R.string.target_spending, targetSpendAmount.asCurrencyAmount()),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }

        val cal = GregorianCalendar.getInstance()
        view.findViewById<TextView>(R.id.weekText).apply {
            text = String.format(getString(R.string.week_order), cal.get(Calendar.WEEK_OF_YEAR))
        }
    }

    override fun invalidate() {
        withState(hvm) {state ->
            when (state.currSpendingAmount) {
                is Success -> {
                    this.currSpendAmount = state.currSpendingAmount.invoke()
                    requireActivity().findViewById<CircularProgressBar>(R.id.weeklySpendingProgress)
                        ?.apply {
                            progress = currSpendAmount / targetSpendAmount
                        }
                    requireActivity().findViewById<TextView>(R.id.currWeeklyText)?.apply {
                        text = HtmlCompat.fromHtml(
                            getString(R.string.current_spending, currSpendAmount.asCurrencyAmount()),
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        )
                    }
                    hvm.updateWeeklySpending(currSpendAmount, targetSpendAmount)
                }
                Uninitialized, is Loading -> TODO()
                is Fail -> TODO()
            }
        }
    }
}