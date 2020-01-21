package com.example.budgetapp

import android.app.Application
import timber.log.Timber

class BudgetApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}