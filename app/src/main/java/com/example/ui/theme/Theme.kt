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

private val MediLightColorScheme = lightColorScheme(
    primary = MediTeal,
    onPrimary = Color.White,
    primaryContainer = MediTealContainer,
    onPrimaryContainer = OnMediTealContainer,
    secondary = MediBlue,
    onSecondary = Color.White,
    secondaryContainer = MediBlueContainer,
    onSecondaryContainer = OnMediBlueContainer,
    tertiary = MediEmerald,
    onTertiary = Color.White,
    tertiaryContainer = MediEmeraldContainer,
    onTertiaryContainer = MediEmeraldDark,
    background = MediClinicalBg,
    onBackground = MediTextPrimary,
    surface = MediSurface,
    onSurface = MediTextPrimary,
    surfaceVariant = MediSurfaceVariant,
    onSurfaceVariant = MediTextSecondary,
    outline = MediBorder,
    error = MediRxRed,
    onError = Color.White,
    errorContainer = MediRxRedLight,
    onErrorContainer = MediRxRed
)

private val MediDarkColorScheme = darkColorScheme(
    primary = MediTealLight,
    onPrimary = Color(0xFF003831),
    primaryContainer = MediTealDark,
    onPrimaryContainer = MediTealContainer,
    secondary = Color(0xFF38BDF8),
    onSecondary = Color(0xFF00354B),
    secondaryContainer = MediBlueDark,
    onSecondaryContainer = MediBlueContainer,
    tertiary = Color(0xFF34D399),
    background = Color(0xFF0F172A),
    onBackground = Color(0xFFF8FAFC),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF8FAFC),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = Color(0xFF475569)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Preserve clinical MediCare brand theme
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> MediDarkColorScheme
        else -> MediLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
