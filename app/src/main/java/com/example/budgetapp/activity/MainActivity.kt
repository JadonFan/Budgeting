package com.example.budgetapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.budgetapp.R
import com.example.budgetapp.fragment.ReportFragment
import com.example.budgetapp.fragment.TransactionTrackerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() ***REMOVED***
    override fun onCreate(savedInstanceState: Bundle?) ***REMOVED***
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setTitle(R.string.app_name)
        showFreshFragment(ReportFragment())

        findViewById<BottomNavigationView>(R.id.navbar).setOnNavigationItemSelectedListener ***REMOVED***
            when (it.itemId) ***REMOVED***
                R.id.myWeeklyReport -> showFreshFragment(ReportFragment())
                R.id.spendingTracker -> showFreshFragment(TransactionTrackerFragment())
                R.id.newsTipsFeed -> ***REMOVED******REMOVED***
    ***REMOVED***
            true
***REMOVED***
***REMOVED***


    override fun onBackPressed() ***REMOVED***
        if (supportFragmentManager.backStackEntryCount > 0) ***REMOVED***
            supportFragmentManager.popBackStack()
***REMOVED*** else ***REMOVED***
            super.onBackPressed()
***REMOVED***
***REMOVED***


    private fun showFreshFragment(fragment: Fragment) ***REMOVED***
        supportFragmentManager.beginTransaction()
            .replace(R.id.keyDisplay, fragment)
            .addToBackStack(null)
            .commit()
***REMOVED***
***REMOVED***
