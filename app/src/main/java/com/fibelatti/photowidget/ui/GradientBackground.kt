package com.epic.widgetwall.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    isDark: Boolean = false,
    content: @Composable BoxScope.() -> Unit,
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    
    val screenWidth = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeight = with(density) { configuration.screenHeightDp.dp.toPx() }
    
    val gradientColors = if (isDark) {
        listOf(
            Color(0xFF1A2A3A), // Dark blue-gray
            Color(0xFF2A4A3A), // Dark green
            Color(0xFF4A4A2A), // Dark yellow
        )
    } else {
        listOf(
            Color(0xFF2A7B9B), // Blue-gray
            Color(0xFF57C785), // Light green
            Color(0xFFEDDD53), // Light yellow
        )
    }
    
    val gradient = Brush.linearGradient(
        colors = gradientColors,
        start = androidx.compose.ui.geometry.Offset(0f, 0f),
        end = androidx.compose.ui.geometry.Offset(screenWidth, screenHeight)
    )
    
    Box(
        modifier = modifier.background(gradient),
        content = content
    )
}

