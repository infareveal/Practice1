package com.example.habitexm.repository

import com.example.habitexm.data.dao.HabitDao
import com.example.habitexm.data.dao.HabitDaysDao
import com.example.habitexm.data.dao.HabitSettingsLogDao
import com.example.habitexm.data.model.Habit
import com.example.habitexm.data.model.HabitDays
import com.example.habitexm.data.model.HabitSettinsLog
import com.example.habitexm.data.model.HabitWithDays
import com.example.habitexm.ui.screens.datamodel.HabitInterval
import com.example.habitexm.usecase.getWindowRange
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class HabitRepository @Inject constructor(private val habitDao: HabitDao, private val habiDaysDao: HabitDaysDao, private val settingLogDao: HabitSettingsLogDao) {

    suspend fun insert(habit: Habit): Long{
        return habitDao.insertHabit(habit)
    }

    suspend fun update(habit: Habit){
        habitDao.updateHabit(habit)
    }

    suspend fun insertOrUpdateLogEntry(log: HabitSettinsLog) {
        settingLogDao.insertOrUpdateLog(log)
    }

    suspend fun getHabitById(habitId: Int): Habit {
        return habitDao.getHabitById(habitId)
    }

    fun getHabitCount(): Flow<Int> {
        return habitDao.getHabitCount()
    }

    fun getAllHabitWithDays(): Flow<List<HabitWithDays>> {
        return habitDao.getAllHabitWithDays()
    }

    suspend fun toggleHabitDay(habitId: Int, epochDay: Long, completed: Boolean) {
        habiDaysDao.toggleOrInsertHabitDay(
            HabitDays(
                habitId = habitId,
                date = epochDay,
                isCompleted = completed,
            )
        )
    }

    suspend fun getAllHabitsSync(): List<Habit> {
        return habitDao.getAllHabits()
    }

    suspend fun getHabitDay(habitId: Int, day: Long): HabitDays?{
        return habiDaysDao.getHabitDay(habitId, day)
    }

    suspend fun getLatestSettingBeforeOrAt(habitId: Int, day: Long): HabitSettinsLog? {
        return settingLogDao.getLatestSettingBeforeOrAt(habitId, day)
    }

    suspend fun getSumInRange(habitId: Int, startDate: Long, endDate: Long): Int {
        return habiDaysDao.getSumInRange(habitId, startDate, endDate)
    }


     suspend fun runUniversalSync(habit: Habit, today: Long){
        val limitDate = if (habit.endDate != null) {
            minOf(today - 1, habit.endDate)
        } else {
            today - 1
        }

//         if (habit.interval != HabitInterval.DAILY) {
//             val todayDate = LocalDate.ofEpochDay(today) //ofEpoch to make work with functions later
//
//             val (start, end) = getWindowRange(
//                 date = todayDate,
//                 interval = habit.interval
//             ) // range for today between its week or month
//
//             val currentProgress =
//                 getSumInRange(habit.id, start.toEpochDay(), end.toEpochDay()) //progress limit until <= targetCount
//
//             val isGoalMet = currentProgress == habit.targetCount //streak +1 ex: 3 completion in a week
//         }

        //Loop through the timeLine to fill "Gaps" if phone was off
        for (day in habit.startDate..limitDate) {
            val existingEntry = getHabitDay(habit.id, day)

            //only fill if user hasn't manually touched this
            if (existingEntry == null || !existingEntry.isCompleted){

                val lastKnownSetting = getLatestSettingBeforeOrAt(habit.id, day)

                if (habit.interval != HabitInterval.DAILY) {
                    val dateObj =
                        LocalDate.ofEpochDay(day) //conert long to Localdate required by getWibndowRange()
                    val (start, end) = getWindowRange(dateObj, habit.interval)
                    val progressInWindow =
                        getSumInRange(habit.id, start.toEpochDay(), end.toEpochDay())

                    if (lastKnownSetting != null) {
                        if (progressInWindow > habit.targetCount) {
                            toggleHabitDay(habit.id, day, false) //false because once goal meet it stops, its nessesary to declare false to create row atleast
                        }
                        toggleHabitDay(habit.id, day, lastKnownSetting.isAutoCompleted)
                    }
                }
                else {
                    //if settinglof found autocomplete that day true or false as based on last known log
                    if (lastKnownSetting != null) {
                        toggleHabitDay(habit.id, day, lastKnownSetting.isAutoCompleted)
                    }
                }
            } //if row exist with completed to true, do nothing
        }
    }
}