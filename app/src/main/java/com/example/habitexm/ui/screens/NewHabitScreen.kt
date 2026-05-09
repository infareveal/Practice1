package com.example.habitexm.ui.screens

import android.R
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.habitexm.navigation.NavRoutes
import com.example.habitexm.ui.components.AdvanceOpition
import com.example.habitexm.ui.components.AdvancedOprionsLayout
import com.example.habitexm.ui.components.GoalCounter
import com.example.habitexm.ui.components.IntervalSelector
import com.example.habitexm.ui.components.colorGridCustom
import com.example.habitexm.ui.components.textCustom
import com.example.habitexm.ui.components.textFieldCustome
import com.example.habitexm.ui.screens.datamodel.HabitInterval
import com.example.habitexm.ui.theme.LocalSpacing
import com.example.habitexm.utility.DateUtils
import com.example.habitexm.utility.HabitEditorState
import com.example.habitexm.viewmodel.NewHabitVM
import com.example.habitexm.utility.ScreenMode

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewHabitScreen(navController: NavHostController) {

    val newHabitVm: NewHabitVM = hiltViewModel()

    //check mode
    var isEditMode = HabitEditorState.mode == ScreenMode.EDIT
    //this load data once when screen opens
    LaunchedEffect(Unit) {
        newHabitVm.loadHabit(HabitEditorState.targetHabitId)
        newHabitVm.savedFinished.collect {
            navController.popBackStack()
        }
    }

    val spacing1 = LocalSpacing.current

    //Ui only states
    var isExpanded by remember { mutableStateOf(false) }
    var currentSelection by remember { mutableStateOf(SelectionType.NONE) }


    // --- DATE PICKER DIALOG LOGIC ---
    if (currentSelection != SelectionType.NONE) {
        // Initial value for the picker based on current VM state
        val initialDate = if (currentSelection == SelectionType.START)
            newHabitVm.startDateEpoch * 86400000L // convert back to millis
        else
            newHabitVm.endDateEpoch?.times(86400000L) ?: System.currentTimeMillis()

        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialDate)

        DatePickerDialog(
            onDismissRequest = { currentSelection = SelectionType.NONE },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val epoch = DateUtils.millisToEpochDay(millis)
                        if (currentSelection == SelectionType.START) newHabitVm.startDateEpoch =
                            epoch
                        else newHabitVm.endDateEpoch = epoch
                    }
                    currentSelection = SelectionType.NONE
                }) { Text("Ok") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    val colorList = listOf(
        Color(0xFFFF6F3C), Color(0xFFFFA500), Color(0xFFFFD700),
        Color(0xFFB2D732), Color(0xFF3CB595), Color(0xFF4AC9FF), Color(0xFF3B9CFF),
        Color(0xFF6366F1), Color(0xFF7C3AED), Color(0xFFE11D74),
        Color(0xFFFF4D4D), Color(0xFF22C55E), Color(0xFFA3A3A3),
        Color(0xFF94A3B8), Color(0xFF67E8F9), Color(0xFF86EFAC), Color(0xFFDCFCE7),
        Color(0xFFFDE68A), Color(0xFFFCD34D), Color(0xFFFBBF24), Color(0xFF86EFAC)
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { topAppBar(navController, NavRoutes.NewHabitScreen.route) }
    ) { padding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding) //calculate only top
                    .background(MaterialTheme.colorScheme.background)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize(),

                    contentPadding = PaddingValues(
                        16.dp,
                        top = 16.dp,
                        end = 16.dp,
                        bottom = 56.dp + spacing1.medium
                    ),
                    verticalArrangement = Arrangement.spacedBy(spacing1.medium)
                ) {
                    item {
                        textCustom(text = "Enter Habit")
                        Spacer(modifier = Modifier.height(spacing1.small))
                        textFieldCustome(
                            value = newHabitVm.habitText,
                            lable = { Text("Daily Gym", fontSize = 28.sp) },
                            minLines = 1,
                            onTextChange = {
                                newHabitVm.habitText = it
                            }
                        )
                    }

                    item {
                        textCustom(text = "Enter Description")
                        Spacer(modifier = Modifier.height(spacing1.small))
                        textFieldCustome(
                            value = newHabitVm.habitDescriptionText,
                            lable = { Text("Description....") },
                            minLines = 3,
                            singleLine = false,
                            onTextChange = { newValue ->
                                newHabitVm.habitDescriptionText = newValue
                            }
                        )
                    }

                    item {
                        textCustom(text = "Select Color")
                        Spacer(modifier = Modifier.height(spacing1.small))

                        colorGridCustom(
                            selectedColor = newHabitVm.selectedColor,
                            onColorChange = { newHabitVm.selectedColor = it },
                            colorList = colorList,
                            spacing1 = spacing1
                        )
                    }

                    item {
                        Switch(
                            checked = newHabitVm.isAutoComplete,
                            onCheckedChange = { newHabitVm.isAutoComplete = it }
                        )
                    }

                    item{
                        IntervalSelector(
                                selectedInterval = newHabitVm.selectedInterval,
                                onIntervalSelected = { newHabitVm.selectedInterval = it }
                        )

                        if (newHabitVm.selectedInterval != HabitInterval.DAILY) {
                            GoalCounter(
                                targetCount = newHabitVm.targetCount,
                                interval = newHabitVm.selectedInterval,
                                onCountChange = { newHabitVm.targetCount = it }
                            )
                        }
                    }

                    item {
                        AdvanceOpition(
                            isExpanded = isExpanded,
                            onExpandChange = { isExpanded = it },
                            spacing1 = spacing1
                        )
                    }

                    item {
                        AnimatedVisibility(
                            visible = isExpanded,
                            enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                            exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                        ) {
                            AdvancedOprionsLayout(
                                formatStartDate = newHabitVm.formatStartDate,
                                formatEndDate = newHabitVm.formatEndDate,
                                onStartClick = { currentSelection = SelectionType.START },
                                onEndClick = { currentSelection = SelectionType.END },
                                spacing1 = spacing1
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(spacing1.small))
                    }
                }

                Button(
                    onClick = {
                        newHabitVm.saveHabit()
                    },
                    modifier = Modifier.align(Alignment.BottomCenter)
                        .fillMaxWidth() // Margin around the button
                        .height(56.dp)
                        .padding(start = 16.dp, end = 16.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF6F00) // Your Orange
                    )
                ) {
                    Text(
                        text = if (isEditMode) "Save" else "Create",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }
        }
    }


enum class SelectionType {NONE, START, END}