package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val ToddlerLightColorScheme = lightColorScheme(
    primary = ToddlerCoral,
    onPrimary = Color.White,
    primaryContainer = ToddlerCoralLight,
    onPrimaryContainer = Color(0xFFBF360C),
    secondary = ToddlerBlue,
    onSecondary = Color.White,
    secondaryContainer = ToddlerBlueLight,
    onSecondaryContainer = Color(0xFF01579B),
    tertiary = ToddlerGreen,
    onTertiary = Color.White,
    tertiaryContainer = ToddlerGreenLight,
    onTertiaryContainer = Color(0xFF1B5E20),
    background = ToddlerBackground,
    onBackground = ToddlerTextPrimary,
    surface = ToddlerSurface,
    onSurface = ToddlerTextPrimary,
    surfaceVariant = Color(0xFFFFF3E0),
    onSurfaceVariant = ToddlerTextSecondary
)

private val ToddlerDarkColorScheme = darkColorScheme(
    primary = ToddlerCoral,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF8C3218),
    onPrimaryContainer = ToddlerCoralLight,
    secondary = ToddlerBlue,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF0D47A1),
    onSecondaryContainer = ToddlerBlueLight,
    tertiary = ToddlerGreen,
    onTertiary = Color.White,
    background = Color(0xFF1A1A24),
    onBackground = Color(0xFFF1F1F1),
    surface = Color(0xFF242432),
    onSurface = Color(0xFFF1F1F1)
)

@Composable
fun ToddlerLearningHubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Preschool apps benefit from fixed joyful bright colors
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> ToddlerDarkColorScheme
        else -> ToddlerLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
