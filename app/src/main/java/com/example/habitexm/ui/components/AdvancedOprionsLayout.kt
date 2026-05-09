package com.example.habitexm.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.habitexm.ui.theme.Spacing

@Composable
fun AdvancedOprionsLayout(formatStartDate: String, formatEndDate: String, onStartClick: () -> Unit, onEndClick: () -> Unit, spacing1: Spacing) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing1.medium)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(spacing1.small)
            ) {
                textCustom(text = "Start Date")

                PickerBox(
                    value = formatStartDate,
                    onClick = { onStartClick() },
                    spacing1 = spacing1
                )
            }

            Spacer(modifier = Modifier.width(spacing1.medium))

            Column(modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(spacing1.small)
            ) {
                textCustom(text = "End Date")

                PickerBox(
                    value = formatEndDate,
                    onClick = onEndClick,
                    spacing1 = spacing1
                )
            }
        }

        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(spacing1.small)
        ) {
            textCustom(text = "Select Reminder time")
        }
    }
}

@Composable
fun PickerBox(value: String, spacing1: Spacing, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(value,
            modifier = Modifier.padding(spacing1.medium),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}