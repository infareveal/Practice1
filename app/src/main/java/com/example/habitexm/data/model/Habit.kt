package com.example.habitexm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.habitexm.ui.screens.datamodel.HabitInterval

@Entity(tableName = "habits")

data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String? = null,
    val startDate: Long,
    val endDate: Long? = null,
    val color: Int,
    val targetCount: Int = 1,
    val interval: HabitInterval = HabitInterval.DAILY,
    val autoComplete: Boolean = false
)