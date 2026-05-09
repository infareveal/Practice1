package com.example.habitexm.navigation

import kotlinx.serialization.Serializable


sealed class NavRoutes(val route: String) {
    object HomeScreen: NavRoutes("Home")

    object NewHabitScreen: NavRoutes("New")

    object SettingScreen: NavRoutes("Setting")
}