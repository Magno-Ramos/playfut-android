package com.magnus.playfut.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.magnus.playfut.R

val AppFontFamily = FontFamily(
    Font(R.font.poppins_thin, FontWeight.Thin),
    Font(R.font.poppins_thin_italic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_semi_bold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.poppins_black, FontWeight.Black),
    Font(R.font.poppins_black_italic, FontWeight.Black, FontStyle.Italic)
)

private val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = AppFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = AppFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = AppFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = AppFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = AppFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = AppFontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = AppFontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = AppFontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = AppFontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = AppFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = AppFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = AppFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = AppFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = AppFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = AppFontFamily),
)
