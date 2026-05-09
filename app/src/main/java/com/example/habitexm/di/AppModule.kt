package com.example.habitexm.di

import android.content.Context
import androidx.room.Room
import com.example.habitexm.data.dao.HabitDao
import com.example.habitexm.data.dao.HabitDaysDao
import com.example.habitexm.data.dao.HabitSettingsLogDao
import com.example.habitexm.data.db.AppDatabase
import com.example.habitexm.data.model.Habit
import com.example.habitexm.data.model.HabitSettinsLog
import com.example.habitexm.repository.HabitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) : AppDatabase{
        return Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "habit_database"
                )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideHabitDao(db: AppDatabase): HabitDao{
        return db.habitDao()
    }

    @Provides
    fun provideHabitDaysDao(db: AppDatabase): HabitDaysDao{
        return db.habitDaysDao()
    }

    @Provides
    fun provideSettinDao(db: AppDatabase): HabitSettingsLogDao{
        return db.settingDao()
    }
}