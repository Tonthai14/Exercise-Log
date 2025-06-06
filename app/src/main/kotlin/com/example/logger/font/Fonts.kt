package com.example.logger.font

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.logger.R

val cascadiaMonoFamily = FontFamily(
    Font(R.font.cascadia_mono_extra_light, FontWeight.ExtraLight),
    Font(R.font.cascadia_mono, FontWeight.Normal),
    Font(R.font.cascadia_mono_bold, FontWeight.Bold),
)

val cascadiaMonoTypography = Typography(
    headlineLarge = TextStyle(fontFamily = cascadiaMonoFamily),
    headlineMedium = TextStyle(fontFamily = cascadiaMonoFamily),
    headlineSmall = TextStyle(fontFamily = cascadiaMonoFamily),
    bodyLarge = TextStyle(fontFamily = cascadiaMonoFamily),
    bodyMedium = TextStyle(fontFamily = cascadiaMonoFamily),
    bodySmall = TextStyle(fontFamily = cascadiaMonoFamily),
    displaySmall = TextStyle(fontFamily = cascadiaMonoFamily),
    displayMedium = TextStyle(fontFamily = cascadiaMonoFamily),
    labelLarge = TextStyle(fontFamily = cascadiaMonoFamily),
)