package com.epic.widgetwall.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.epic.widgetwall.R

@Composable
fun GradientFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconRes: Int = R.drawable.ic_new_widget,
    contentDescription: String? = "Add Widget",
) {
    val gradientBrush = Brush.radialGradient(
        colors = listOf(
            Color(0xFF2A7B9B), // Blue-gray
            Color(0xFF57C785), // Light green
            Color(0xFFEDDD53), // Light yellow
        ),
        radius = 100f
    )
    
    Box(
        modifier = modifier
            .size(72.dp)
            .background(
                brush = gradientBrush,
                shape = CircleShape
            )
            .clickable(
                onClick = onClick,
                role = Role.Button
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier.size(32.dp)
        )
    }
}
