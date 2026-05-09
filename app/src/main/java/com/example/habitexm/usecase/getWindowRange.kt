package com.example.habitexm.usecase

import com.example.habitexm.ui.screens.datamodel.HabitInterval
import java.time.LocalDate

fun getWindowRange(date: LocalDate, interval: HabitInterval): Pair<LocalDate, LocalDate> {
    return when (interval){
        HabitInterval.DAILY -> date to date

        HabitInterval.WEEKLY -> {
            val start = date.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))
            start to start.plusDays(6) //monday to sunday
        }

        HabitInterval.MONTHLY -> {
            val start = date.withDayOfMonth(1)
            start to start.withDayOfMonth(date.lengthOfMonth()) //1st to 30th/31st "check for each country"
        }
    }
}