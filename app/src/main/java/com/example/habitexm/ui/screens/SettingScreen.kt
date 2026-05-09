package com.example.habitexm.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.habitexm.navigation.NavRoutes

@Composable
fun SettingScreen(navController: NavHostController) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { topAppBar(navController, NavRoutes.SettingScreen.route) },
        bottomBar = { bottomAppBar(navController, NavRoutes.SettingScreen.route)}
    ) { padding ->
        Column (
            modifier = Modifier.padding(padding)
        ){
            Text(text = "Setting screee", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground)
        }
    }
}