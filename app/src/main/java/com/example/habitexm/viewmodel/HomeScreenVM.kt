package com.example.habitexm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitexm.repository.HabitRepository
import com.example.habitexm.ui.screens.datamodel.HabitInterval
import com.example.habitexm.ui.screens.datamodel.HomeUiModel
import com.example.habitexm.usecase.GetHomeScreenUseCase
import com.example.habitexm.usecase.getWindowRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(private val repo: HabitRepository, private val getHomeScreenUseCase: GetHomeScreenUseCase): ViewModel() {

    init {
        healDatabaseGap()
    }

     fun healDatabaseGap() {
        viewModelScope.launch(Dispatchers.IO) {
            val allHabits = repo.getAllHabitsSync()
            val today = LocalDate.now().toEpochDay()

            allHabits.forEach { habit -> repo.runUniversalSync(habit, today) }
        }
    }

    // StateFlow<List<HomeUiModel>>
    val habitState = repo.getAllHabitWithDays()
        .map { rawList -> //emmited list from flow
            getHomeScreenUseCase(rawList)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    fun toggleHabitDay(habitId: Int, epochDay: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            // We'll create this repo function to handle the manual toggle
            repo.toggleHabitDay(habitId, epochDay, isCompleted)
        }
    }
}