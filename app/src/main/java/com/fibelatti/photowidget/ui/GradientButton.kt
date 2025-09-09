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
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF2A7B9B), // Blue-gray
            Color(0xFF57C785), // Light green
            Color(0xFFEDDD53), // Light yellow
        )
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
            color = Color.White, // White text on gradient button
            style = MaterialTheme.typography.labelLarge
        )
    }
}
