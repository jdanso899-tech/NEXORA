package com.nexora.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val NexoraBlue = Color(0xFF38BDF8)
val NexoraPurple = Color(0xFFA78BFA)
val NexoraDark = Color(0xFF0F172A)
val NexoraSurface = Color(0xFF1E293B)
val NexoraMuted = Color(0xFF94A3B8)

private val DarkColorScheme = darkColorScheme(
    primary = NexoraBlue,
    secondary = NexoraPurple,
    background = NexoraDark,
    surface = NexoraSurface,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = NexoraMuted
)

@Composable
fun NexoraTheme(
    darkTheme: Boolean = true, // force dark for the product look
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography(),
        content = content
    )
}
