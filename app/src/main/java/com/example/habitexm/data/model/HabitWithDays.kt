package com.example.habitexm.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class HabitWithDays(
    @Embedded
    val habit: Habit,

    @Relation(
        parentColumn = "id",
        entityColumn = "habitId"
    )
    val days: List<HabitDays>
)
