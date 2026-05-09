package com.example.habitexm.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.habitexm.ui.screens.datamodel.HabitInterval

@Composable
fun IntervalSelector(
    selectedInterval: HabitInterval,
    onIntervalSelected: (HabitInterval) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Interval", color = Color.White, style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HabitInterval.entries.forEach { interval ->
                val isSelected = selectedInterval == interval
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onIntervalSelected(interval) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) Color(0xFF3D5AFE) else Color(0xFF1E1E1E),
                    border = BorderStroke(1.dp, if (isSelected) Color.Transparent else Color.Gray)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = interval.name.lowercase().replaceFirstChar { it.uppercase() },
                            color = if (isSelected) Color.White else Color.Gray
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun GoalCounter(
    targetCount: Int,
    interval: HabitInterval,
    onCountChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("Goal", color = Color.White)
            Text(
                text = "$targetCount / ${interval.name.lowercase()}",
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { if (targetCount > 1) onCountChange(targetCount - 1) }) {
                Icon(Icons.Default.Delete, contentDescription = null, tint = Color.White)
            }
            Text(
                text = targetCount.toString(),
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            IconButton(onClick = { onCountChange(targetCount + 1) }) {
                Icon(Icons.Default.AddCircle, contentDescription = null, tint = Color.White)
            }
        }
    }
}