package com.alithya.common.ui.themes

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme  = darkColorScheme(
    primary = PrimaryDark15,
    onPrimary = PrimaryDark100,
    primaryContainer = PrimaryDark90,
    onPrimaryContainer = PrimaryDark15,
    secondary = SecondaryDark20,
    onSecondary = SecondaryDark100,
    secondaryContainer = SecondaryDark50,
    onSecondaryContainer = SecondaryDark10,
    tertiary = TertiaryDark40,
    onTertiary = TertiaryDark100,
    tertiaryContainer = TertiaryDark90,
    onTertiaryContainer = TertiaryDark10,
    error = ErrorDark40,
    onError = ErrorDark100,
    errorContainer = ErrorDark90,
    onErrorContainer = ErrorDark10,
    background = BackgroundDark90,
    onBackground = BackgroundDark10,
    surface = SurfaceDark90,
    onSurface = SurfaceDark10,
    surfaceVariant = SurfaceVariantDark90,
    onSurfaceVariant = SurfaceVariantDark30,
    outline = OutlineDark50,
    scrim = IceBlueDark100
)

private val LightColorScheme  = lightColorScheme(
    primary = Primary15,
    onPrimary = Primary100,
    primaryContainer = Primary90,
    onPrimaryContainer = Primary15,
    secondary = Secondary20,
    onSecondary = Secondary100,
    secondaryContainer = Secondary50,
    onSecondaryContainer = Secondary10,
    tertiary = Tertiary40,
    onTertiary = Tertiary100,
    tertiaryContainer = Tertiary90,
    onTertiaryContainer = Tertiary10,
    error = Error40,
    onError = Error100,
    errorContainer = Error90,
    onErrorContainer = Error10,
    background = Background90,
    onBackground = Background10,
    surface = Surface90,
    onSurface = Surface10,
    surfaceVariant = SurfaceVariant90,
    onSurfaceVariant = SurfaceVariant30,
    outline = Outline50,
    scrim = IceBlue90
)

@Composable
fun EasyChatTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.onBackground.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    CompositionLocalProvider(LocalDimension provides Dimensions()){
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = shapes,
            content = content,
        )
    }
}