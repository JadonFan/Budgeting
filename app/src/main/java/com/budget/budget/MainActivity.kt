package com.budget.budget

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.budget.budget.ui.home.HomeFragment
import com.budget.budget.ui.transaction.TransactionTrackerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setTitle(R.string.app_name)

        findViewById<BottomNavigationView>(R.id.nav_bar).setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.myWeeklyReport -> showFreshFragment(HomeFragment())
                R.id.spendingTracker -> showFreshFragment(TransactionTrackerFragment())
                R.id.newsTipsFeed -> {}
            }
            true
        }

        if (savedInstanceState == null) {
            showFreshFragment(HomeFragment())

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
