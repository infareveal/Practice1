package com.example.habitexm.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "habit_settings_log",
    indices = [Index(value = ["habitId", "date"], unique = true)]
)
data class HabitSettinsLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val habitId: Int,
    val date: Long,
    val isAutoCompleted: Boolean,
    val timestamp: Long
)