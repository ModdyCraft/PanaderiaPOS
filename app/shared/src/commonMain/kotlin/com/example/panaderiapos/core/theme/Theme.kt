package com.example.panaderiapos.core.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val OrangePrimary = Color(0xFFE65100)
val OrangeSecondary = Color(0xFFFF9800)
val DarkComplementary = Color(0xFF1A237E)
val DeepBackgroundDark = Color(0xFF0B1021)

val TextOnDark = Color(0xFFF5F5F5)
val CardBackground = Color(0xFF1E2436).copy(alpha = 0.85f)

val PanaderiaColorScheme = darkColorScheme(
    primary = OrangePrimary,
    secondary = OrangeSecondary,
    background = Color.Transparent,
    surface = CardBackground,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = TextOnDark,
    onSurface = TextOnDark
)

val BackgroundGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xD84315),
        DarkComplementary,
        DeepBackgroundDark
    ), start = Offset(0f, 0f), end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
)