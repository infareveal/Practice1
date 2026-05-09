package com.example.habitexm.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.disableHotReloadMode
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun textFieldCustome(lable: @Composable () -> Unit, singleLine: Boolean = true, minLines: Int, onTextChange: (String) -> Unit, value: String?) {
    TextField(
        value = value?:"",
        onValueChange = onTextChange, //reference
        placeholder = lable, //reference
        modifier = Modifier.fillMaxWidth(),
        minLines = minLines,
        singleLine = singleLine,
        textStyle = MaterialTheme.typography.titleLarge,
        shape = MaterialTheme.shapes.small,
        colors = TextFieldDefaults.colors(
            unfocusedLabelColor = MaterialTheme.colorScheme.surfaceDim,
            focusedLabelColor = MaterialTheme.colorScheme.surfaceDim,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        )
    )
}