package com.example.budgetapp.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkPalette = darkColors(
    primary = Color(0x008577),
    primaryVariant = Color(0x00574B),
    onPrimary = Color.White,
    surface = Color.Black,
)

@Composable
fun AppTheme(content: @Composable () -> Unit) =
    MaterialTheme(
        colors = DarkPalette,
        content = content,
        typography = AppTypography
    )