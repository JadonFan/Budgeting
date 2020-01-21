package com.example.budgetapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.budgetapp.R
import com.example.budgetapp.fragment.ReportFragment
import com.example.budgetapp.fragment.TransactionTrackerFragment
import com.example.budgetapp.util.DateUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setTitle(R.string.app_name)

        findViewById<BottomNavigationView>(R.id.nav_bar).setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.myWeeklyReport -> showFreshFragment(ReportFragment())
                R.id.spendingTracker -> showFreshFragment(TransactionTrackerFragment())
                R.id.newsTipsFeed -> {}
            }
            true
        }

        if (savedInstanceState == null) {
            showFreshFragment(ReportFragment())

        }
    }


    override fun onBackPressed() =
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }


    private fun showFreshFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction()
            .replace(R.id.key_display, fragment)
            .addToBackStack(null)
            .commit()
}
