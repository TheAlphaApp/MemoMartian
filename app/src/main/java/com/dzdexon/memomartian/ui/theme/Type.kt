package com.dzdexon.memomartian.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dzdexon.memomartian.R

val poppinsFamily = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semi_bold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_black, FontWeight.Black),
    Font(R.font.poppins_extra_bold, FontWeight.ExtraBold),
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_thin, FontWeight.Thin),
    Font(R.font.poppins_extra_light, FontWeight.ExtraLight),
)
val latoFamily = FontFamily(
    Font(R.font.lato_regular, FontWeight.Normal),
    Font(R.font.lato_bold, FontWeight.Bold),
    Font(R.font.lato_black, FontWeight.Black),
    Font(R.font.lato_light, FontWeight.Light),
    Font(R.font.lato_thin, FontWeight.Thin),
    Font(R.font.lato_italic, style = FontStyle.Italic),
)
private val defaultTypography = Typography()
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = defaultTypography.bodyLarge.copy(
        fontFamily = latoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = latoFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = latoFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = latoFamily),
    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = latoFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = latoFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = latoFamily),
    titleLarge = defaultTypography.titleLarge.copy(fontFamily = latoFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = latoFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = latoFamily),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = latoFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = latoFamily),
    labelLarge = defaultTypography.labelLarge.copy(fontFamily = latoFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = latoFamily),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = latoFamily),
    /* Other default text styles to override
    titleLarge = defaultTypography.displayLarge.copy(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = defaultTypography.displayLarge.copy.copy(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
