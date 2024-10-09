package com.dzdexon.memomartian.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


object DarkThemeColors {
    val accent = Color(0xffD71921)
    val primary = Color(0xff000000)
    val secondary = Color(0xff1b1b1d)
    val tertiary = Color(0xff2C2F34)
    val onPrimary = Color(0xffFFFFFF)
    val onSecondary = Color(0xffAEABB1)
    val onTertiary = Color(0xff6C696E)

}
object LightThemeColors {
    val accent = Color(0xffD71921) // Keeping the accent color the same for brand consistency
    val primary = Color(0xffFFFFFF) // Light background color
    val secondary = Color(0xffF5F5F5) // A very light gray for secondary backgrounds
    val tertiary = Color(0xffE0E0E0) // Slightly darker gray for tertiary elements
    val onPrimary = Color(0xff000000) // Black text/icons on a white background
    val onSecondary = Color(0xff4D4D4D) // Darker gray text/icons on lighter gray backgrounds
    val onTertiary = Color(0xff707070) // Medium gray text/icons on tertiary gray background
}
// Define a CompositionLocal to provide access to CustomColors
val LocalCustomColors = staticCompositionLocalOf<CustomColors> {
    error("No CustomColors provided") // Will throw an error if no CustomColors are provided
}


// Common class to hold theme colors
data class CustomColors(
    val accent: Color,
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val onTertiary: Color
)

// Dark theme colors
val DarkColors = CustomColors(
    accent = DarkThemeColors.accent,
    primary = DarkThemeColors.primary,
    secondary = DarkThemeColors.secondary,
    tertiary = DarkThemeColors.tertiary,
    onPrimary = DarkThemeColors.onPrimary,
    onSecondary = DarkThemeColors.onSecondary,
    onTertiary = DarkThemeColors.onTertiary
)

// Light theme colors
val LightColors = CustomColors(
    accent = LightThemeColors.accent,
    primary = LightThemeColors.primary,
    secondary = LightThemeColors.secondary,
    tertiary = LightThemeColors.tertiary,
    onPrimary = LightThemeColors.onPrimary,
    onSecondary = LightThemeColors.onSecondary,
    onTertiary = LightThemeColors.onTertiary
)