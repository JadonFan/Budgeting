package com.example.budgetapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.budgetapp.R
import com.example.budgetapp.fragment.ReportFragment
import com.example.budgetapp.fragment.TransactionTrackerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setTitle(R.string.app_name)
        showFreshFragment(ReportFragment())

        findViewById<BottomNavigationView>(R.id.navbar).setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.myWeeklyReport -> showFreshFragment(ReportFragment())
                R.id.spendingTracker -> showFreshFragment(TransactionTrackerFragment())
                R.id.newsTipsFeed -> {}
            }
            true
        }
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }


    private fun showFreshFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.keyDisplay, fragment)
            .addToBackStack(null)
            .commit()
    }
}
