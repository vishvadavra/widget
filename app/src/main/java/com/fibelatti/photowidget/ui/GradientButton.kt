package com.epic.widgetwall.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isDark: Boolean = false,
) {
    val gradientColors = if (isDark) {
        listOf(
            Color(0xFF8B6B6B), // Dark pinkish-nude
            Color(0xFFA67A7A), // Medium dark pinkish-nude
            Color(0xFFC48B8B), // Light dark pinkish-nude
        )
    } else {
        listOf(
            Color(0xFFD4A5A5), // Dusty rose pinkish-nude (top)
            Color(0xFFE8B4B4), // Medium pinkish-nude (middle)
            Color(0xFFFDF8F8), // Very light cream (bottom)
        )
    }
    
    val gradientBrush = Brush.verticalGradient(
        colors = gradientColors
    )
    
    Box(
        modifier = modifier
            .background(
                brush = gradientBrush,
                shape = MaterialTheme.shapes.large
            )
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button
            )
            .padding(horizontal = 24.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isDark) Color.White else Color(0xFF2D1B0A),
            style = MaterialTheme.typography.labelLarge
        )
    }
}
