package com.example.habitexm.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.habitexm.navigation.NavRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBar(navController: NavHostController, currentRoute: String?) {
    TopAppBar(
        title = {
            when(currentRoute) {
                NavRoutes.HomeScreen.route -> {
                    Row {
                        Text(
                            text = "Consistent",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "H",
                            color = Color.Red
                        )
                    }
                }

                NavRoutes.NewHabitScreen.route -> {
                    Text(
                        text = "New Habit",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                NavRoutes.SettingScreen.route -> {
                    Text(
                        text = "Settings",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                else -> Text("text")
            }
        },

        navigationIcon = {
            if (currentRoute == NavRoutes.NewHabitScreen.route) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }
            }
        }
    )
}