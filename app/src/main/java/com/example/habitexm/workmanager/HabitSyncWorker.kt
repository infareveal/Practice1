package com.example.habitexm.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.habitexm.repository.HabitRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate

@HiltWorker
class HabitSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repo: HabitRepository
) : CoroutineWorker(context, workerParams){

    override suspend fun doWork(): Result {
        return try {
            val allHabits = repo.getAllHabitsSync() //list of habit
            val today = LocalDate.now().toEpochDay()

            allHabits.forEach { habit ->
                repo.runUniversalSync(habit, today)
            }

            Result.success()
        } catch (e: Exception) {
            Result.retry() //if db is locked, try again
        }
    }
}