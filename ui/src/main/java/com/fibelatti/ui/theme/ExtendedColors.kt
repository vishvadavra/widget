@file:Suppress("Unused")

package com.epic.widgetwall.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2A7B9B), // Blue-gray from gradient
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFFFFF), // White container
    onPrimaryContainer = Color(0xFF2A7B9B),

    secondary = Color(0xFF57C785), // Light green from gradient
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFFFFF), // White container
    onSecondaryContainer = Color(0xFF57C785),

    tertiary = Color(0xFFEDDD53), // Light yellow from gradient
    onTertiary = Color(0xFF2A7B9B),
    tertiaryContainer = Color(0xFFFFFFFF), // White container
    onTertiaryContainer = Color(0xFFEDDD53),

    error = Color(0xFFD2691E), // Warm error color
    errorContainer = Color(0xFFFFE4D1),
    onError = Color(0xFFFFFFFF),
    onErrorContainer = Color(0xFF5D2C0A),

    background = Color(0xFF2A7B9B), // Blue-gray background (will be overridden by gradient)
    onBackground = Color(0xFFFFFFFF),

    surface = Color(0xFFFFFFFF), // White surface for cards
    onSurface = Color(0xFF2A7B9B),
    surfaceVariant = Color(0xFFFFFFFF), // White surface variant
    onSurfaceVariant = Color(0xFF2A7B9B),

    outline = Color(0xFF2A7B9B), // Blue-gray outline
    outlineVariant = Color(0xFF57C785),

    inverseSurface = Color(0xFF2A7B9B),
    inverseOnSurface = Color(0xFFFFFFFF),
    inversePrimary = Color(0xFF57C785),
)

internal val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF57C785), // Light green for dark theme
    onPrimary = Color(0xFF1A2A3A),
    primaryContainer = Color(0xFFFFFFFF), // White container
    onPrimaryContainer = Color(0xFF2A7B9B),

    secondary = Color(0xFFEDDD53), // Light yellow
    onSecondary = Color(0xFF1A2A3A),
    secondaryContainer = Color(0xFFFFFFFF), // White container
    onSecondaryContainer = Color(0xFF57C785),

    tertiary = Color(0xFF2A7B9B), // Blue-gray
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFFFFF), // White container
    onTertiaryContainer = Color(0xFFEDDD53),

    error = Color(0xFFFFB4AB),
    errorContainer = Color(0xFF8B4513), // Dark error
    onError = Color(0xFF1A2A3A),
    onErrorContainer = Color(0xFFFFE4D1),

    background = Color(0xFF1A2A3A), // Dark blue-gray background (will be overridden by gradient)
    onBackground = Color(0xFFFFFFFF),

    surface = Color(0xFFFFFFFF), // White surface for cards
    onSurface = Color(0xFF1A2A3A),
    surfaceVariant = Color(0xFFFFFFFF), // White surface variant
    onSurfaceVariant = Color(0xFF1A2A3A),

    outline = Color(0xFF2A7B9B), // Blue-gray outline
    outlineVariant = Color(0xFF57C785),

    inverseSurface = Color(0xFFFFFFFF),
    inverseOnSurface = Color(0xFF1A2A3A),
    inversePrimary = Color(0xFF57C785),
)
