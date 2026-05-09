package com.example.habitexm.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitexm.data.model.Habit
import com.example.habitexm.data.model.HabitSettinsLog
import com.example.habitexm.repository.HabitRepository
import com.example.habitexm.utility.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.habitexm.ui.screens.datamodel.HabitInterval
import com.example.habitexm.utility.HabitEditorState
import com.example.habitexm.utility.ScreenMode
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@HiltViewModel
class NewHabitVM @Inject constructor(private val repo: HabitRepository): ViewModel() {

    private val _saveFinished = MutableSharedFlow<Unit>()
    val savedFinished = _saveFinished.asSharedFlow()


    var isDataReady by mutableStateOf(false)
    var habitText by mutableStateOf("")
    var habitDescriptionText by mutableStateOf("")
    var selectedColor by mutableStateOf(Color(0xFF7C3AED))
    var startDateEpoch by mutableStateOf(java.time.LocalDate.now().toEpochDay())
    var endDateEpoch by mutableStateOf<Long?>(null)
    val formatStartDate: String get() = DateUtils.formatEpochToLocale(startDateEpoch)
    val formatEndDate: String get() = DateUtils.formatEpochToLocale(endDateEpoch)
    var isAutoComplete by mutableStateOf(false)
    var selectedInterval by mutableStateOf(HabitInterval.DAILY)
    var targetCount by mutableIntStateOf(1)

    fun loadHabit(id: Int) {
        if (id == -1) {
            resetFields()
            return
        }

        if (HabitEditorState.mode == ScreenMode.EDIT) {
            viewModelScope.launch {
                repo.getHabitById(HabitEditorState.targetHabitId)?.let { habit ->
                    habitText = habit.title
                    habitDescriptionText = habit.description ?: ""
                    selectedColor = Color(habit.color)
                    startDateEpoch = habit.startDate
                    endDateEpoch = habit.endDate
                    isAutoComplete = habit.autoComplete
                    selectedInterval = habit.interval
                    targetCount = habit.targetCount
                }
            }
        }
    }

    fun resetFields(){
         habitText = ""
         habitDescriptionText = ""
         selectedColor = Color(0xFF7C3AED)
         startDateEpoch = java.time.LocalDate.now().toEpochDay()
         endDateEpoch = null
         isAutoComplete = false
         selectedInterval = HabitInterval.DAILY
         targetCount = 1
    }

        fun saveHabit() {
            viewModelScope.launch {
                val isEdit = HabitEditorState.mode == ScreenMode.EDIT
                val targetId = HabitEditorState.targetHabitId

                val habit = Habit(
                    id = if (isEdit) targetId else 0,
                    title = habitText,
                    description = habitDescriptionText,
                    color = selectedColor.toArgb(),
                    startDate = startDateEpoch,
                    endDate = endDateEpoch,
                    autoComplete = isAutoComplete,
                    targetCount = targetCount,
                    interval = selectedInterval
                )

                //capture correct id
                val finalHabitid = if (isEdit) {
                    repo.update(habit)
                    targetId
                } else {
                    repo.insert(habit).toInt()
                }

                //log entry after save or update each time
                val today = java.time.LocalDate.now().toEpochDay()
                val logEntry = HabitSettinsLog(
                    habitId = finalHabitid,
                    date = today,
                    isAutoCompleted = isAutoComplete,
                    timestamp = System.currentTimeMillis()
                )

                repo.insertOrUpdateLogEntry(logEntry)

                //reset state after db work is complete
                HabitEditorState.mode = ScreenMode.NEW
                HabitEditorState.targetHabitId = -1

                _saveFinished.emit(Unit)
            }
        }
    }
