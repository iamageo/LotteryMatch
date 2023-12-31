package com.iamageo.lotterymatch.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NumbersRow(gameNumbers: String, otpText: String) {
    val numbers = gameNumbers.split(',').map { it.trim() }
    val otpNumbers = otpText.chunked(2)

    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp, alignment = Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        numbers.forEach { number ->
            val backgroundColor = if (otpNumbers.contains(number)) {
                MaterialTheme.colors.primary
            } else {
                MaterialTheme.colors.background
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(50.dp)
                    .background(backgroundColor, CircleShape)
            ) {
                Text(
                    text = number,
                    style = MaterialTheme.typography.body1,
                    color = Color.Black
                )
            }
        }
    }
}