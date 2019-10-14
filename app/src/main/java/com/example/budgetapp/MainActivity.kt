package com.example.budgetapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.budgetapp.fragment.ReportFragment
import com.example.budgetapp.fragment.TrackerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() ***REMOVED***
    override fun onCreate(savedInstanceState: Bundle?) ***REMOVED***
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setTitle(R.string.app_name)
        showFreshFragment(ReportFragment())

        findViewById<BottomNavigationView>(R.id.navbar).setOnNavigationItemSelectedListener ***REMOVED***
            when (it.itemId) ***REMOVED***
                R.id.my_report -> ***REMOVED***
                    Toast.makeText(this, "HELLO", Toast.LENGTH_SHORT).show()
                    showFreshFragment(ReportFragment())
        ***REMOVED***
                R.id.spending_tracker -> ***REMOVED***
                    Toast.makeText(this, "WORLD", Toast.LENGTH_SHORT).show()
                    showFreshFragment(TrackerFragment())
        ***REMOVED***
    ***REMOVED***
            true
***REMOVED***
***REMOVED***

    private fun showFreshFragment(fragment: Fragment) ***REMOVED***
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.keyDisplay, fragment)
        ft.commit()
***REMOVED***
***REMOVED***
