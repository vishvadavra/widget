package com.epic.widgetwall.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradientTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    isDark: Boolean = false,
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    
    val screenWidth = with(density) { configuration.screenWidthDp.dp.toPx() }
    
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
    
    val gradient = Brush.horizontalGradient(
        colors = gradientColors,
        startX = 0f,
        endX = screenWidth
    )
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(gradient)
    ) {
        TopAppBar(
            title = title,
            navigationIcon = navigationIcon ?: {},
            actions = actions,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White,
                navigationIconContentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
