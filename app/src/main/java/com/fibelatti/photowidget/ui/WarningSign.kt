package com.epic.widgetwall.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.epic.widgetwall.R

@Composable
fun WarningSign(
    text: String,
    modifier: Modifier = Modifier,
    showDismissButton: Boolean = false,
    dismissButtonText: String = stringResource(R.string.photo_widget_action_got_it),
    onDismissClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFD2691E).copy(alpha = 0.2f), // Warm nude warning
                        Color(0xFF8B6F47).copy(alpha = 0.15f), // Primary nude
                        Color(0xFFA68B6B).copy(alpha = 0.1f) // Tertiary nude
                    )
                ),
                shape = MaterialTheme.shapes.medium,
            )
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
        

            Text(
                text = text,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodySmall,
            )
        }

        if (showDismissButton) {
            Text(
                text = dismissButtonText,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable(
                        onClick = onDismissClick,
                        role = Role.Button,
                    ),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}
