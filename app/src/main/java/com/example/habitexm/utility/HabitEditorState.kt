package com.example.habitexm.utility

object HabitEditorState {
    var mode: ScreenMode = ScreenMode.NEW
    var targetHabitId: Int = -1
}

enum class ScreenMode{
    NEW,
    EDIT
}