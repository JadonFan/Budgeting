@file:JvmName("CurrencyUtils")

package com.budget.budget.util

import android.icu.util.Currency
import android.icu.util.CurrencyAmount
import android.os.Build
import java.text.NumberFormat
import java.util.*

fun Number?.asCurrencyAmount(): String {
    val amount = this ?: 0f
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        CurrencyAmount(
            amount,
            Currency.getInstance("CAD")
        ).toString()
    } else {
        NumberFormat.getCurrencyInstance(Locale.CANADA).format(amount)
    }
}
