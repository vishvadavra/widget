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
            Color(0xFF1A0F0F), // Very dark pinkish-nude
            Color(0xFF2A1A1A), // Dark pinkish-nude
            Color(0xFF4A2A2A), // Medium dark pinkish-nude
        )
    } else {
        listOf(
            Color(0xFFFDF8F8), // Very light cream
            Color(0xFFF9F0F0), // Light pinkish-nude
            Color(0xFFF7E7E7), // Medium light pinkish-nude
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

