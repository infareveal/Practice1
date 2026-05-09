package com.example.habitexm.data.model

import androidx.room.TypeConverter
import com.example.habitexm.ui.screens.datamodel.HabitInterval

class HabitConverters {
        @TypeConverter
        fun fromInterval(value: HabitInterval?): String? = value?.name

        @TypeConverter
        fun toInterval(value: String?): HabitInterval? {
            return value?.let { HabitInterval.valueOf(it) }
        }
    }
