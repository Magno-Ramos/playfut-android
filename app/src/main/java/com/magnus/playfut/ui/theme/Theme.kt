package com.magnus.playfut.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val lightScheme = lightColorScheme(
    primary = Color.primaryLight,
    onPrimary = Color.onPrimaryLight,
    primaryContainer = Color.primaryContainerLight,
    onPrimaryContainer = Color.onPrimaryContainerLight,
    secondary = Color.secondaryLight,
    onSecondary = Color.onSecondaryLight,
    secondaryContainer = Color.secondaryContainerLight,
    onSecondaryContainer = Color.onSecondaryContainerLight,
    tertiary = Color.tertiaryLight,
    onTertiary = Color.onTertiaryLight,
    tertiaryContainer = Color.tertiaryContainerLight,
    onTertiaryContainer = Color.onTertiaryContainerLight,
    error = Color.errorLight,
    onError = Color.onErrorLight,
    errorContainer = Color.errorContainerLight,
    onErrorContainer = Color.onErrorContainerLight,
    background = Color.backgroundLight,
    onBackground = Color.onBackgroundLight,
    surface = Color.surfaceLight,
    onSurface = Color.onSurfaceLight,
    surfaceVariant = Color.surfaceVariantLight,
    onSurfaceVariant = Color.onSurfaceVariantLight,
    outline = Color.outlineLight,
    outlineVariant = Color.outlineVariantLight,
    scrim = Color.scrimLight,
    inverseSurface = Color.inverseSurfaceLight,
    inverseOnSurface = Color.inverseOnSurfaceLight,
    inversePrimary = Color.inversePrimaryLight,
    surfaceDim = Color.surfaceDimLight,
    surfaceBright = Color.surfaceBrightLight,
    surfaceContainerLowest = Color.surfaceContainerLowestLight,
    surfaceContainerLow = Color.surfaceContainerLowLight,
    surfaceContainer = Color.surfaceContainerLight,
    surfaceContainerHigh = Color.surfaceContainerHighLight,
    surfaceContainerHighest = Color.surfaceContainerHighestLight,
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightScheme,
        content = content,
        typography = AppTypography
    )
}

