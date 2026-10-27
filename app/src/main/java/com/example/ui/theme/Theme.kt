package com.example.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalAppThemePreset = staticCompositionLocalOf { AppThemePreset.DARK_CONSTANT }

fun buildColorScheme(preset: AppThemePreset): ColorScheme {
    return darkColorScheme(
        primary = preset.primaryColor,
        onPrimary = Color.White,
        primaryContainer = preset.primaryColor.copy(alpha = 0.25f),
        onPrimaryContainer = Color.White,
        secondary = preset.secondaryColor,
        onSecondary = Color.White,
        secondaryContainer = preset.secondaryColor.copy(alpha = 0.25f),
        onSecondaryContainer = Color.White,
        tertiary = preset.tagColor,
        onTertiary = Color.White,
        tertiaryContainer = preset.tagColor.copy(alpha = 0.25f),
        background = preset.backgroundColor,
        onBackground = TextPrimaryDark,
        surface = preset.surfaceColor,
        onSurface = TextPrimaryDark,
        surfaceVariant = preset.surfaceVariantColor,
        onSurfaceVariant = TextSecondaryDark,
        error = ErrorDark,
        onError = Color.White,
        errorContainer = ErrorContainerDark
    )
}

@Composable
fun MyApplicationTheme(
    preset: AppThemePreset = AppThemePreset.DARK_CONSTANT,
    content: @Composable () -> Unit
) {
    val colorScheme = buildColorScheme(preset)

    CompositionLocalProvider(LocalAppThemePreset provides preset) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
