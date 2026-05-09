package com.example.habitexm.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.habitexm.data.model.HabitDays

@Dao
interface HabitDaysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun toggleOrInsertHabitDay(habitDay: HabitDays)

    @Query("SELECT * FROM habit_days WHERE habitId = :habitId AND date = :date LIMIT 1")
    suspend fun getHabitDay(habitId: Int, date: Long): HabitDays?

    @Query("""
    SELECT COUNT(*) FROM habit_days 
    WHERE habitId = :habitId 
    AND date BETWEEN :startDate AND :endDate 
    AND isCompleted = 1
""")
    suspend fun getSumInRange(habitId: Int, startDate: Long, endDate: Long): Int
}