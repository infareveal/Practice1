package com.example.habitexm.ui.screens.datamodel

import android.accessibilityservice.GestureDescription

data class HomeUiModel(
    val id: Int = 0,
    val title: String,
    val description: String? = null,
    val startDate: Long,
    val endDate: Long? = null,
    val color: Int,
    val last7Days: List<DayDisplayModel>,
    val interval: HabitInterval = HabitInterval.DAILY,
    val currentProgress: Int = 0,
    val targetCount: Int
)

data class DayDisplayModel(
    val dayName: String,
    val dayNumber: String,
    val epochDay: Long,
    val isToday: Boolean,
    val dayStatus: DayStatus
)

enum class DayStatus{
    COMPLETED,
    MISSED,
    PENDING,
    FUTURE
}