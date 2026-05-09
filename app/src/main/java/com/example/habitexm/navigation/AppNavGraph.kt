package com.example.habitexm.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.habitexm.ui.screens.HomeScreen
import com.example.habitexm.ui.screens.NewHabitScreen
import com.example.habitexm.ui.screens.SettingScreen

@Composable
fun AppNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HomeScreen.route
    ){
        // HOME SCREEN
        composable(
            route = NavRoutes.HomeScreen.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            HomeScreen(navController)
        }

        composable(
            route = NavRoutes.NewHabitScreen.route,
            // 1. Entering "New" from anywhere (Home or Settings)
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(400)
                )
            },
            // 2. Leaving "New" to go forward to something else
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(400)
                )
            },
            // 3. Returning TO "New" because you popped the screen above it
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(400)
                )
            },
            // 4. Leaving "New" because you clicked Back/Cancel (Settings/Home)
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(400)
                )
            }
        ) {
            NewHabitScreen(navController)
        }

        // SETTINGS SCREEN
        composable(
            route = NavRoutes.SettingScreen.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            SettingScreen(navController)
        }

        // DELETE the extra NewHabitScreen composable you had here!
    }
}