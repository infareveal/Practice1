package com.example.habitexm.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.habitexm.data.model.HabitSettinsLog

@Dao
interface HabitSettingsLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateLog(log: HabitSettinsLog)

    @Query("SELECT * FROM habit_settings_log WHERE habitId = :habitId AND date <= :targetDate ORDER BY date DESC, timestamp DESC LIMIT 1")
    suspend fun getLatestSettingBeforeOrAt(habitId: Int, targetDate: Long): HabitSettinsLog?
}