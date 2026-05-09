package com.example.habitexm.di

import androidx.work.Configuration
import androidx.hilt.work.HiltWorkerFactory
import javax.inject.Inject

@dagger.hilt.android.HiltAndroidApp
class HabitApplication : android.app.Application(), androidx.work.Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
