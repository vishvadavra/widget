@file:Suppress("Unused")

package com.epic.widgetwall.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal val LightColorScheme = lightColorScheme(
    primary = Color(0xFFD4A5A5), // Dusty rose pinkish-nude
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFF7E7E7), // Light pinkish-nude
    onPrimaryContainer = Color(0xFF2D1B0A),

    secondary = Color(0xFFE8B4B4), // Medium pinkish-nude
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFF9F0F0), // Very light pinkish-nude
    onSecondaryContainer = Color(0xFF2D1B0A),

    tertiary = Color(0xFFF0C4C4), // Soft pinkish-nude
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFDF8F8), // Cream pinkish-nude
    onTertiaryContainer = Color(0xFF2D1B0A),

    error = Color(0xFFD2691E), // Warm error color
    errorContainer = Color(0xFFFFE4D1),
    onError = Color(0xFFFFFFFF),
    onErrorContainer = Color(0xFF5D2C0A),

    background = Color(0xFFFDF8F8), // Very light cream background
    onBackground = Color(0xFF2D1B0A),

    surface = Color(0xFFFDF8F8), // Very light cream surface
    onSurface = Color(0xFF2D1B0A),
    surfaceVariant = Color(0xFFF9F0F0), // Light pinkish-nude variant
    onSurfaceVariant = Color(0xFF5D4A3A),

    outline = Color(0xFFD4A5A5), // Pinkish-nude outline
    outlineVariant = Color(0xFFE8B4B4),

    inverseSurface = Color(0xFF2D1B0A),
    inverseOnSurface = Color(0xFFF9F0F0),
    inversePrimary = Color(0xFFE8B4B4),
)

internal val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFE8B4B4), // Light pinkish-nude for dark theme
    onPrimary = Color(0xFF2D1B0A),
    primaryContainer = Color(0xFF8B6B6B), // Dark pinkish-nude container
    onPrimaryContainer = Color(0xFFF9F0F0),

    secondary = Color(0xFFF0C4C4), // Medium light pinkish-nude
    onSecondary = Color(0xFF2D1B0A),
    secondaryContainer = Color(0xFF6B4A4A), // Dark pinkish-nude secondary
    onSecondaryContainer = Color(0xFFF7E7E7),

    tertiary = Color(0xFFF5D4D4), // Soft light pinkish-nude
    onTertiary = Color(0xFF2D1B0A),
    tertiaryContainer = Color(0xFF4A2A2A), // Dark pinkish-nude tertiary
    onTertiaryContainer = Color(0xFFFDF8F8),

    error = Color(0xFFFFB4AB),
    errorContainer = Color(0xFF8B4513), // Dark error
    onError = Color(0xFF2D1B0A),
    onErrorContainer = Color(0xFFFFE4D1),

    background = Color(0xFF1A0F0F), // Very dark pinkish-nude background
    onBackground = Color(0xFFF9F0F0),

    surface = Color(0xFF1A0F0F), // Very dark pinkish-nude surface
    onSurface = Color(0xFFF9F0F0),
    surfaceVariant = Color(0xFF2A1A1A), // Dark pinkish-nude variant
    onSurfaceVariant = Color(0xFFE8B4B4),

    outline = Color(0xFFD4A5A5), // Pinkish-nude outline
    outlineVariant = Color(0xFF6B4A4A),

    inverseSurface = Color(0xFFF9F0F0),
    inverseOnSurface = Color(0xFF1A0F0F),
    inversePrimary = Color(0xFFD4A5A5),
)
