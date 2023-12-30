package com.iamageo.lotterymatch.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.iamageo.lotterymatch.theme.Shapes

@Composable
fun LotteryTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colors = CustomDarkColorPalette,
        typography = CustomTypography,
        shapes = Shapes,
        content = content
    )
}