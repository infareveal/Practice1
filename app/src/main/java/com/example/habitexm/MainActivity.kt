package com.example.habitexm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.habitexm.ui.screens.MainScreen
import com.example.habitexm.ui.theme.HabitexmTheme
import com.example.habitexm.workmanager.HabitSyncWorker
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitexmTheme {
                MainScreen()
            }
        }
        scheduleHabitSync()
    }

    private fun scheduleHabitSync() {
        val now = LocalDateTime.now()
        val midnight = now.plusDays(1).withHour(0).withMinute(1) // 12:01 AM tomorrow
        val delayInMinutes = Duration.between(now, midnight).toMinutes()

//        val constraints = Constraints.Builder()
//            .setRequiresBatteryNotLow(true)
//            .build()

        val syncRequest = PeriodicWorkRequestBuilder<HabitSyncWorker>(
            24, TimeUnit.HOURS // Run once a day
        )
            .setInitialDelay(delayInMinutes, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "HabitDailySync",
            ExistingPeriodicWorkPolicy.KEEP, // Don't restart the 24h timer if it's already scheduled
            syncRequest
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HabitexmTheme {
        Greeting("Android")
    }
}