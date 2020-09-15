package com.example.budgetapp.home

import com.example.budgetapp.database.AppDatabase
import com.example.budgetapp.util.DateUtils
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val db: AppDatabase,
) {

    private val fs: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getCurrSpendingAmount(from: Date, to: Date) =
        db.spendingDao().findWeeklySpending(from, to)

    /**
     * TODO use an AlarmManager to schedule the update every 7 days
     * Intentionally does not work as permissions are set such that READ and WRITE operations are both currently disabled
     */
    fun updateWeeklySpending(currSpendAmount: Float, targetSpendAmount: Float) {
        val weeklyData = hashMapOf(
            "week" to DateUtils.findWeekRange(),
            "year" to GregorianCalendar.YEAR,
            "spending" to currSpendAmount,
            "target" to targetSpendAmount
        )
        fs.collection("weekly_data")
            .document("${GregorianCalendar.YEAR}${DateUtils.findWeekRange()}")
            .set(weeklyData)
            .addOnSuccessListener {
                println("SUCCESS")
            }
            .addOnFailureListener {
                println("FAILED: $it")
            }
    }
}