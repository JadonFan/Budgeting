package com.budget.budget.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class TopMenuAction(@StringRes val labelId: Int, @DrawableRes val iconId: Int) {
}