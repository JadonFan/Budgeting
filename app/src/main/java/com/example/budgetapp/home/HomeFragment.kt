package com.example.budgetapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.example.budgetapp.R
import com.example.budgetapp.components.CircularProgressBar
import dagger.hilt.android.AndroidEntryPoint
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
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.currTargetText).apply {
            text = HtmlCompat.fromHtml(
                String.format("Target: <b>$%.2f</b>", targetSpendAmount),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }

        val cal: Calendar = GregorianCalendar.getInstance()
        view.findViewById<TextView>(R.id.weekText).apply {
            text = String.format(getString(R.string.week_order), cal.get(Calendar.WEEK_OF_YEAR))
        }
    }

    override fun invalidate() {
        withState(hvm) {state ->
            if (state.currSpendingAmount is Success) {
                this.currSpendAmount = state.currSpendingAmount.invoke()
                requireActivity().findViewById<CircularProgressBar>(R.id.weeklySpendingProgress)?.apply {
                    progress = currSpendAmount / targetSpendAmount
                }
                requireActivity().findViewById<TextView>(R.id.currWeeklyText)?.apply {
                    text = HtmlCompat.fromHtml(
                        String.format("Current: <b>$%.2f</b>", currSpendAmount),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                }
                hvm.updateWeeklySpending(currSpendAmount, targetSpendAmount)
            }
        }
    }
}