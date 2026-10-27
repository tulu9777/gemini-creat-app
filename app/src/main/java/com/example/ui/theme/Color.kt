package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Default Obsidian & Midnight Dark Theme Tokens
val BackgroundDark = Color(0xFF0B0F19)
val SurfaceDark = Color(0xFF131B2E)
val SurfaceVariantDark = Color(0xFF1E293B)
val SurfaceHighlightDark = Color(0xFF26334D)

// Neon & Cyber Vibrance Accents
val PrimaryIndigo = Color(0xFF818CF8)
val PrimaryLight = Color(0xFFA5B4FC)
val PrimaryContainerDark = Color(0xFF312E81)
val OnPrimaryDark = Color(0xFF0F172A)

val SecondaryCyan = Color(0xFF38BDF8)
val SecondaryContainerDark = Color(0xFF075985)

val TertiaryPink = Color(0xFFF472B6)
val TertiaryContainerDark = Color(0xFF831843)

val AccentGold = Color(0xFFFBBF24)
val AccentEmerald = Color(0xFF34D399)

val TextPrimaryDark = Color(0xFFF8FAFC)
val TextSecondaryDark = Color(0xFF94A3B8)
val TextMutedDark = Color(0xFF64748B)

val ErrorDark = Color(0xFFF87171)
val ErrorContainerDark = Color(0xFF7F1D1D)

/**
 * App Theme Presets available in the app.
 * Allows the user to toggle between Dark Constant, Dark Red, Midnight Gold, Emerald Matrix,
 * Amethyst Violet, and OLED Pure Black.
 */
enum class AppThemePreset(
    val id: String,
    val displayName: String,
    val subtitle: String,
    val primaryColor: Color,
    val secondaryColor: Color,
    val backgroundColor: Color,
    val surfaceColor: Color,
    val surfaceVariantColor: Color,
    val surfaceHighlightColor: Color,
    val tagColor: Color
) {
    DARK_CONSTANT(
        id = "dark_constant",
        displayName = "Dark Constant",
        subtitle = "Cyber Obsidian & Neon Indigo",
        primaryColor = Color(0xFF818CF8),
        secondaryColor = Color(0xFF38BDF8),
        backgroundColor = Color(0xFF0B0F19),
        surfaceColor = Color(0xFF131B2E),
        surfaceVariantColor = Color(0xFF1E293B),
        surfaceHighlightColor = Color(0xFF26334D),
        tagColor = Color(0xFFFBBF24)
    ),
    DARK_RED(
        id = "dark_red",
        displayName = "Dark Red / Crimson",
        subtitle = "Blood Ruby & Obsidian Ember",
        primaryColor = Color(0xFFFF3358),
        secondaryColor = Color(0xFFFB7185),
        backgroundColor = Color(0xFF0D0406),
        surfaceColor = Color(0xFF1A0A0E),
        surfaceVariantColor = Color(0xFF2E1219),
        surfaceHighlightColor = Color(0xFF3D1822),
        tagColor = Color(0xFFFF4D6D)
    ),
    MIDNIGHT_GOLD(
        id = "midnight_gold",
        displayName = "Midnight Gold",
        subtitle = "Royal Luxury & Amber Glow",
        primaryColor = Color(0xFFFBBF24),
        secondaryColor = Color(0xFFF59E0B),
        backgroundColor = Color(0xFF0E0D09),
        surfaceColor = Color(0xFF1A1710),
        surfaceVariantColor = Color(0xFF2B261A),
        surfaceHighlightColor = Color(0xFF3D3624),
        tagColor = Color(0xFFFDE047)
    ),
    EMERALD_MATRIX(
        id = "emerald_matrix",
        displayName = "Emerald Matrix",
        subtitle = "Cyberpunk Mint & Neon Jade",
        primaryColor = Color(0xFF10B981),
        secondaryColor = Color(0xFF34D399),
        backgroundColor = Color(0xFF040F0A),
        surfaceColor = Color(0xFF0B1E16),
        surfaceVariantColor = Color(0xFF122F23),
        surfaceHighlightColor = Color(0xFF1A4030),
        tagColor = Color(0xFF6EE7B7)
    ),
    AMETHYST_VIOLET(
        id = "amethyst_violet",
        displayName = "Amethyst Violet",
        subtitle = "Deep Mystical Purple & Fuchsia",
        primaryColor = Color(0xFFA855F7),
        secondaryColor = Color(0xFFEC4899),
        backgroundColor = Color(0xFF0C0614),
        surfaceColor = Color(0xFF170C26),
        surfaceVariantColor = Color(0xFF26143D),
        surfaceHighlightColor = Color(0xFF361D57),
        tagColor = Color(0xFFE879F9)
    ),
    OLED_PITCH_BLACK(
        id = "oled_pitch_black",
        displayName = "OLED Pure Black",
        subtitle = "True 0-Black & Electric Ice",
        primaryColor = Color(0xFF38BDF8),
        secondaryColor = Color(0xFFE2E8F0),
        backgroundColor = Color(0xFF000000),
        surfaceColor = Color(0xFF101012),
        surfaceVariantColor = Color(0xFF1E1E22),
        surfaceHighlightColor = Color(0xFF2C2C33),
        tagColor = Color(0xFF38BDF8)
    );

    companion object {
        fun fromId(id: String): AppThemePreset {
            return entries.find { it.id == id } ?: DARK_CONSTANT
        }
    }
}
