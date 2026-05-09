package com.example.habitexm.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val AppTypoGraphy = Typography(
    titleLarge = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.ExtraBold
    ),

    titleMedium = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    ),

    headlineMedium = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal
    ),

    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),

    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),

    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    ),
)