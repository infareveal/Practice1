package com.example.habitexm.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

data class Spacing(
    val small:  androidx.compose.ui.unit.Dp = 6.dp,
    val medium: androidx.compose.ui.unit.Dp = 12.dp,
    val large: androidx.compose.ui.unit.Dp = 16.dp,
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }
