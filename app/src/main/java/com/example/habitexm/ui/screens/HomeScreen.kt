package com.example.habitexm.ui.screens

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.habitexm.navigation.NavRoutes
import com.example.habitexm.ui.screens.datamodel.DayDisplayModel
import com.example.habitexm.ui.screens.datamodel.DayStatus
import com.example.habitexm.ui.screens.datamodel.HabitInterval
import com.example.habitexm.ui.screens.datamodel.HomeUiModel
import com.example.habitexm.utility.HabitEditorState
import com.example.habitexm.viewmodel.HomeScreenVM
import com.example.habitexm.utility.ScreenMode
import java.time.LocalDate

@Composable
fun HomeScreen(navController: NavHostController) {
    val homeScreenVm: HomeScreenVM = hiltViewModel()
    // Observe the StateFlow from ViewModel
    val habits by homeScreenVm.habitState.collectAsState()

        val context = LocalContext.current

        // 1. Create a state that holds "Today".
        // When this changes, the UI is forced to recompose.
        var currentDay by remember { mutableLongStateOf(LocalDate.now().toEpochDay()) }

        // 2. The "Midnight Watcher": Listens for the System to say the date changed.
        DisposableEffect(Unit) {
            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    // Update our state, which triggers the LaunchedEffect below
                    currentDay = LocalDate.now().toEpochDay()
                }
            }
            val filter = IntentFilter().apply {
                addAction(Intent.ACTION_DATE_CHANGED)
                addAction(Intent.ACTION_TIMEZONE_CHANGED)
                addAction(Intent.ACTION_TIME_CHANGED)
            }
            context.registerReceiver(receiver, filter)
            onDispose { context.unregisterReceiver(receiver) }
        }

        // 3. The "Action": When the day changes, run the healer.
        // This will update the DB, which triggers your StateFlow to emit the NEW day.
        LaunchedEffect(currentDay) {
            homeScreenVm.healDatabaseGap()
        }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { topAppBar(navController, NavRoutes.HomeScreen.route) },
        bottomBar = { bottomAppBar(navController, NavRoutes.HomeScreen.route)}
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(habits, key = { it.id }) { habit ->
                HabitItem(
                    habit = habit,
                    onDayClick = { epochDay, wasCompleted ->
                        homeScreenVm.toggleHabitDay(habit.id, epochDay, !wasCompleted)
                    },
                    navController
                )
            }
        }
    }
}

@Composable
fun HabitItem(habit: HomeUiModel, onDayClick: (Long, Boolean) -> Unit, navController: NavHostController) {
    Card(modifier = Modifier.fillMaxWidth().clickable {
        HabitEditorState.mode = ScreenMode.EDIT
        HabitEditorState.targetHabitId = habit.id
        navController.navigate(NavRoutes.NewHabitScreen.route)
    }) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = habit.title, style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                habit.last7Days.forEach { day ->
                    DayColumn(day = day, color = Color(habit.color), onDayClick = onDayClick)
                }
            }

            // Show text progress only for Daily/Monthly
            if (habit.interval != HabitInterval.DAILY) {
                Text(
                    text = "${habit.currentProgress}/${habit.targetCount}",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color(habit.color)
                )
            }
        }

        // Progress Bar: Only for Daily/Monthly
        if (habit.interval != HabitInterval.DAILY) {
            val progress = habit.currentProgress.toFloat() / habit.targetCount.coerceAtLeast(1)
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clip(CircleShape),
                color = Color(habit.color),
                trackColor = Color(habit.color).copy(alpha = 0.2f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        }
    }


@Composable
fun DayColumn(day: DayDisplayModel, color: Color, onDayClick: (Long, Boolean) -> Unit) {
    val isEnabled = day.isToday // Practice Rule: Only today is clickable

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(enabled = isEnabled) {
            onDayClick(day.epochDay, day.dayStatus == DayStatus.COMPLETED)
        }
    ) {
        Text(text = day.dayName, style = MaterialTheme.typography.labelSmall) //jjsjsj
        Text(text = day.dayNumber, style = MaterialTheme.typography.bodyMedium)

        // Simple indicator for status
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(
                    when (day.dayStatus) {
                        DayStatus.COMPLETED -> color
                        DayStatus.PENDING -> color.copy(alpha = 0.2f)
                        DayStatus.MISSED -> Color.Red.copy(alpha = 0.2f)
                        else -> Color.Gray.copy(alpha = 0.1f)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            if (day.dayStatus == DayStatus.COMPLETED) {
                Icon(Icons.Default.Check, "", modifier = Modifier.size(16.dp), tint = Color.White)
            }
        }
    }
}