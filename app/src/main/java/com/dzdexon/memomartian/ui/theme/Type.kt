package com.dzdexon.memomartian.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
private val defaultTypography = Typography()
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = defaultTypography.bodyLarge.copy(
        fontFamily = poppinsFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = poppinsFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = poppinsFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = poppinsFamily),
    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = poppinsFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = poppinsFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = poppinsFamily),
    titleLarge = defaultTypography.titleLarge.copy(fontFamily = poppinsFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = poppinsFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = poppinsFamily),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = poppinsFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = poppinsFamily),
    labelLarge = defaultTypography.labelLarge.copy(fontFamily = poppinsFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = poppinsFamily),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = poppinsFamily),
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
