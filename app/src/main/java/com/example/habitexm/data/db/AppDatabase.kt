package com.example.habitexm.data.db

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.habitexm.data.dao.HabitDao
import com.example.habitexm.data.dao.HabitDaysDao
import com.example.habitexm.data.dao.HabitSettingsLogDao
import com.example.habitexm.data.model.Habit
import com.example.habitexm.data.model.HabitConverters
import com.example.habitexm.data.model.HabitDays
import com.example.habitexm.data.model.HabitSettinsLog


@Database(
    entities = [Habit::class, HabitDays::class, HabitSettinsLog::class],
    version = 5,
    exportSchema = false
)
@TypeConverters(HabitConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun habitDaysDao(): HabitDaysDao
    abstract fun settingDao(): HabitSettingsLogDao
}