package com.example.budgetapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.budgetapp.fragment.ReportFragment
import com.example.budgetapp.fragment.TrackerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setTitle(R.string.app_name)
        showFreshFragment(ReportFragment())

        findViewById<BottomNavigationView>(R.id.navbar).setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.my_report -> {
                    Toast.makeText(this, "HELLO", Toast.LENGTH_SHORT).show()
                    showFreshFragment(ReportFragment())
                }
                R.id.spending_tracker -> {
                    Toast.makeText(this, "WORLD", Toast.LENGTH_SHORT).show()
                    showFreshFragment(TrackerFragment())
                }
            }
            true
        }
    }

    private fun showFreshFragment(fragment: Fragment) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.keyDisplay, fragment)
        ft.commit()
    }
}
