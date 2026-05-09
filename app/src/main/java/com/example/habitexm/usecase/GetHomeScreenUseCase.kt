package com.example.habitexm.usecase

import com.example.habitexm.data.model.HabitWithDays
import com.example.habitexm.ui.screens.datamodel.DayDisplayModel
import com.example.habitexm.ui.screens.datamodel.DayStatus
import com.example.habitexm.ui.screens.datamodel.HomeUiModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

class GetHomeScreenUseCase @Inject constructor(){

    operator fun invoke(habitWithDays: List<HabitWithDays>): List<HomeUiModel> {

        val today = LocalDate.now().toEpochDay()
        val todayDate = LocalDate.now()

        val last7EpochDays = (0..6).map {
            today - it
        }.reversed()

        return habitWithDays.map { item ->
            val interval = item.habit.interval
            val (windowStart, windowEnd) = getWindowRange(todayDate, interval)
            val currentProgress = item.days.count { dayRow ->
                dayRow.isCompleted && dayRow.date >= windowStart.toEpochDay() && dayRow.date <= windowEnd.toEpochDay()
            }

            HomeUiModel(
                id = item.habit.id,
                title = item.habit.title,
                description = item.habit.description,
                color = item.habit.color,
                startDate = item.habit.startDate,
                endDate = item.habit.endDate,
                interval = interval,
                targetCount = item.habit.targetCount,
                currentProgress = currentProgress,
                last7Days = last7EpochDays.map { epoch ->
                    val date = LocalDate.ofEpochDay(epoch)

                    val dayRow = item.days.find {it.date == epoch} //object return that satisfies condition

                    val status = when {
                        dayRow?.isCompleted == true -> DayStatus.COMPLETED
                        epoch == today -> DayStatus.PENDING
                        epoch < today -> DayStatus.MISSED
                        else -> DayStatus.FUTURE
                    }

                    DayDisplayModel(
                        dayName = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                        dayNumber = date.dayOfMonth.toString(),
                        epochDay = epoch,
                        isToday = epoch == today,
                        dayStatus = status
                    )
                }
            )
        }

    }
}