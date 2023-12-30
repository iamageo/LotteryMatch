package com.iamageo.lotterymatch.theme

import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.iamageo.lotterymatch.R

val paleWhite = Color(0xCCF3F7F9)
val paleBlack = Color(0xff222325)

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val RedA100 = Color(0xffFF8A80)
val PinkA100 = Color(0xffFF80AB)
val PurpleA100 = Color(0xffEA80FC)
val DeepPurpleA100 = Color(0xffB388FF)
val IndigoA100 = Color(0xff8C9EFF)
val BlueA100 = Color(0xff82B1FF)

val Red700 = Color(0xffdd0d3c)
val Red800 = Color(0xffd00036)
val Red900 = Color(0xffc20029)

val CustomDarkColorPalette = darkColors(
    primary = Color.White,
    primaryVariant = Color.Gray,
    secondary = Color.Black
)

val CustomLightColorsPalette = lightColors(
    primary = Red700,
    primaryVariant = Red900,
    onPrimary = Color.White,
    secondary = Red700,
    secondaryVariant = Red900,
    onSecondary = Color.White,
    error = Red800
)

private val montserrat = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_medium, FontWeight.W500),
    Font(R.font.montserrat_semibold, FontWeight.W600)
)

private val domine = FontFamily(
    Font(R.font.domine_regular),
    Font(R.font.domine_bold, FontWeight.Bold)
)

val CustomTypography = Typography(
    h4 = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.W600,
        fontSize = 30.sp
    ),
    h5 = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.W600,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontFamily = domine,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = montserrat,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp
    )
)
