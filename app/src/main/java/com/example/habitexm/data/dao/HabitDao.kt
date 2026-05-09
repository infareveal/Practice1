package com.example.habitexm.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.habitexm.data.model.Habit
import com.example.habitexm.data.model.HabitWithDays
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit): Long

    @Update
    suspend fun updateHabit(habit: Habit)

    @Query("SELECT * FROM habits WHERE id = :habitId")
    suspend fun getHabitById(habitId: Int): Habit

    @Query("SELECT COUNT(*) FROM habits")
    fun getHabitCount(): Flow<Int>

    @Transaction
    @Query("SELECT * FROM habits")
    fun getAllHabitWithDays(): Flow<List<HabitWithDays>>

    @Query("SELECT * FROM habits")
    suspend fun getAllHabits(): List<Habit>
}