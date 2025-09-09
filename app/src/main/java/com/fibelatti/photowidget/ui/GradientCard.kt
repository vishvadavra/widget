package com.epic.widgetwall.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GradientCard(
    modifier: Modifier = Modifier,
    isDark: Boolean = false,
    content: @Composable BoxScope.() -> Unit,
) {
    val cardColor = Color.White
    
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = cardColor,
                    shape = MaterialTheme.shapes.large
                )
                .padding(20.dp),
            content = content
        )
    }
}
