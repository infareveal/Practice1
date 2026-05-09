package com.example.habitexm.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.example.habitexm.navigation.NavRoutes
import com.example.habitexm.utility.HabitEditorState
import com.example.habitexm.utility.ScreenMode

@Composable
fun bottomAppBar(navController: NavHostController, currentRoute: String?) {

    val listNavItems = listOf(
        NavItems("Home", Icons.Default.Home, NavRoutes.HomeScreen.route),
        NavItems("Create", Icons.Default.Create, NavRoutes.NewHabitScreen.route),
        NavItems("Settings", Icons.Default.Settings, NavRoutes.SettingScreen.route)
    )

    NavigationBar {
        listNavItems.forEach { item ->
            val isSelected = currentRoute == item.route
        NavigationBarItem(
            selected = isSelected,
            onClick = {
                if (item.route == NavRoutes.NewHabitScreen.route) {
                    HabitEditorState.mode = ScreenMode.NEW
                    HabitEditorState.targetHabitId = -1

                    navController.navigate(item.route) {
                        launchSingleTop = true
//                        restoreState = true
                    }
                } else {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
            icon = {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title
                )
            },
            label = { Text(item.title) }
        )
        }
    }
}

data class NavItems (
    val title: String,
    val icon: ImageVector,
    val route: String
)