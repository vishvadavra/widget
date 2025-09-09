package com.epic.widgetwall.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun MyWidgetBadge(
    text: String,
    backgroundColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    useGradient: Boolean = true,
) {
    val gradientBrush = if (useGradient) {
        Brush.linearGradient(
            colors = listOf(
                backgroundColor,
                backgroundColor.copy(alpha = 0.8f),
                backgroundColor.copy(alpha = 0.6f)
            )
        )
    } else null
    
    Row(
        modifier = modifier
            .background(
                brush = gradientBrush ?: Brush.linearGradient(listOf(backgroundColor)),
                shape = MaterialTheme.shapes.large
            )
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            color = contentColor,
            style = MaterialTheme.typography.labelLarge,
        )

        if (icon != null) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = contentColor,
            )
        }
    }
}
