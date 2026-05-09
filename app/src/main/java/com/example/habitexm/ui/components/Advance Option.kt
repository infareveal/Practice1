package com.example.habitexm.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import com.example.habitexm.ui.theme.Spacing

@Composable
fun AdvanceOpition(isExpanded: Boolean, onExpandChange: (Boolean) -> Unit, spacing1: Spacing) {
    val rotationalAngle by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f)

    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable { onExpandChange(!isExpanded) }
            .padding(vertical = spacing1.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.onSurfaceVariant)

        Row(
            modifier = Modifier.padding(horizontal = spacing1.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            textCustom(
                text = "Advanced options"
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.rotate(rotationalAngle)
            )
        }

        Divider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.onSurfaceVariant)

    }
}