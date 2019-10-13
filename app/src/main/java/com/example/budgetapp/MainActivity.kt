package com.example.budgetapp

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.example.budgetapp.view.CircularProgressBar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() ***REMOVED***
    override fun onCreate(savedInstanceState: Bundle?) ***REMOVED***
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setTitle(R.string.app_name)

        var currSpendAmount: Float = 100f
        var targetSpendAmount: Float = 500f

        val tf: Typeface = Typeface.createFromAsset(assets, "Roboto-Regular.ttf")
        findViewById<TextView>(R.id.curr_spending).apply ***REMOVED***
            typeface = tf
            text = HtmlCompat.fromHtml(
            String.format("Current: <b>$%.2f</b>", currSpendAmount),
            HtmlCompat.FROM_HTML_MODE_LEGACY)
***REMOVED***
        findViewById<TextView>(R.id.target_spending).apply ***REMOVED***
            typeface = tf
            text = HtmlCompat.fromHtml(
                String.format("Target: <b>$%.2f</b>", targetSpendAmount),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
***REMOVED***

        val spendingProgress = findViewById<CircularProgressBar>(R.id.weekly_spending_progress)
        spendingProgress.progress = currSpendAmount / targetSpendAmount

        findViewById<BottomNavigationView>(R.id.navbar).setOnNavigationItemSelectedListener ***REMOVED***
            when (it.itemId) ***REMOVED***
                R.id.my_report -> Toast.makeText(this, "HELLO", Toast.LENGTH_SHORT).show()
                R.id.spending_tracker -> Toast.makeText(this, "WORLD", Toast.LENGTH_SHORT).show()
    ***REMOVED***
            true
***REMOVED***
***REMOVED***
***REMOVED***
