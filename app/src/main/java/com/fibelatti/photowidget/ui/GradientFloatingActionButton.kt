package com.epic.widgetwall.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GradientFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val gradientBrush = Brush.radialGradient(
        colors = listOf(
            Color(0xFFD4A5A5), // Dusty rose pinkish-nude
            Color(0xFFE8B4B4), // Medium pinkish-nude
            Color(0xFFF0C4C4), // Soft pinkish-nude
        ),
        radius = 100f
    )
    
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = gradientBrush,
                    shape = CircleShape
                )
                .padding(16.dp)
        ) {
            content()
        }
    }
}
