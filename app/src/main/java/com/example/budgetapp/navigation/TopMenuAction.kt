package com.example.budgetapp.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class TopMenuAction(@StringRes val label: Int, @DrawableRes val icon: Int) {
}