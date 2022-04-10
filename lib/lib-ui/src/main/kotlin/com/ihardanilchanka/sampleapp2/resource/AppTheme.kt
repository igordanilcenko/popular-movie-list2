package com.ihardanilchanka.sampleapp2.resource

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val LightColors = lightColors(
    primary = AppColor.purple500,
    primaryVariant = AppColor.purple700,
    onPrimary = AppColor.white,
    secondary = AppColor.teal200,
    secondaryVariant = AppColor.teal700,
    onSecondary = AppColor.black,
    surface = AppColor.white,
    background = AppColor.blueGray100,
)

private val DarkColors = lightColors(
    primary = AppColor.purple200,
    primaryVariant = AppColor.purple700,
    onPrimary = AppColor.black,
    secondary = AppColor.teal200,
    secondaryVariant = AppColor.teal200,
    onSecondary = AppColor.black,
    surface = AppColor.black,
    background = AppColor.blueGray900,
)

val AppTypography = Typography(
    body2 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    overline = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 1.5.sp,
        fontFeatureSettings = "c2sc, smcp" // all caps
    )
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        typography = AppTypography,
        content = content,
    )
}
