package com.example.habitexm.ui.theme

import android.adservices.appsetid.AppSetId
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext



private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary, //Button
    onPrimary = DarkTextPrimary, // Text & Icon on Button
    background = DarkBackground, // Background
    onBackground = DarkTextPrimary, // Text on Background
    surface = DarkSurface, // Surface
    onSurface = DarkTextPrimary, // Text & Icon on Surface
    onSurfaceVariant = DarkTextSecondary, // Secondary Text on Surface
    surfaceDim = DarkTextMuted, // Tertiary Text on Surface
    outline = DarkBorder, // Border
    surfaceContainerLowest = DarkDisabled // Disabled Text or Background
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightTextPrimary,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    onSurfaceVariant = LightTextSecondary,
    surfaceDim = LightTextMuted,
    outline = LightBorder,
    surfaceContainerLowest = LightDisabled
)


@Composable
fun HabitexmTheme(
    darkTheme: Boolean = true,
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(
        LocalSpacing provides Spacing()
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypoGraphy,
            shapes = AppShapes,
            content = content
        )
    }
}