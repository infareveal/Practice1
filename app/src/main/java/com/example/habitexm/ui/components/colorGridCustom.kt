package com.example.habitexm.ui.components

import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontSynthesis.Companion.Weight
import androidx.compose.ui.unit.dp
import com.example.habitexm.ui.theme.LightTextPrimary
import com.example.habitexm.ui.theme.Spacing

@Composable
fun colorGridCustom(
    selectedColor: Color,
    onColorChange: (Color) -> Unit,
    colorList: List<Color>,
    spacing1: Spacing
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.surface)
            .padding(spacing1.medium),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val rows = colorList.chunked(7)

        rows.forEach { rowColors ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                rowColors.forEach { color ->
                    ColorBox(
                        color = color,
                        onClick = onColorChange,
                        isSelected = color == selectedColor,
                        modifier = Modifier.weight(1f).aspectRatio(1f),
                        shape = MaterialTheme.shapes.small
                    )
                }
            }
        }
    }
}

@Composable
fun ColorBox(
    color: Color,
    isSelected: Boolean,
    onClick: (Color) -> Unit,
    modifier: Modifier,
    shape: CornerBasedShape
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(color)
            .clickable { onClick(color) }
    ){
        if (isSelected) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = LightTextPrimary,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(4.dp)
            )
        }
    }
}