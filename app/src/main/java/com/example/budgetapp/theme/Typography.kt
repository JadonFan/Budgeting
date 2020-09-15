package com.example.budgetapp.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.font
import androidx.compose.ui.text.font.fontFamily
import com.example.budgetapp.R

private val robotoFontFamily = fontFamily(font(R.font.roboto_regular))

val AppTypography = Typography(defaultFontFamily = robotoFontFamily)