package com.iamageo.lotterymatch.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


fun isValidNumber(number: String): Boolean {
    return number.toIntOrNull()?.let { it <= 60 } ?: false
}

fun isValidInput(text: String): Boolean {
    return text.chunked(2)
        .all { isValidNumber(it) }
}

fun hasNoDuplicates(text: String): Boolean {
    val numbers = text.chunked(2)
        .mapNotNull { it.toIntOrNull() }
    return numbers.size == numbers.toSet().size
}

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            //throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    BasicTextField(
        modifier = modifier.fillMaxWidth(),
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            val formattedValue = it.text.filter { char -> char.isDigit() }
            if (formattedValue.length <= otpCount * 2 &&
                isValidInput(formattedValue) &&
                hasNoDuplicates(formattedValue)) {
                onOtpTextChange.invoke(formattedValue, formattedValue.length == otpCount * 2)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText
                    )
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val charStartIndex = index * 2
    val charEndIndex = charStartIndex + 1

    val isFocused = text.length in charStartIndex..charEndIndex

    val chars = text.getOrNull(charStartIndex)?.toString().orEmpty() +
            text.getOrNull(charEndIndex)?.toString().orEmpty()

    Text(
        modifier = Modifier
            .width(50.dp)
            .height(50.dp)
            .border(
                1.dp, when {
                    isFocused -> MaterialTheme.colors.primary
                    else -> Color.Gray
                }, RoundedCornerShape(8.dp)
            )
            .padding(2.dp),
        text = chars,
        style = MaterialTheme.typography.h4,
        color = if (isFocused) {
            MaterialTheme.colors.primary
        } else {
            Color.Gray
        },
        textAlign = TextAlign.Center
    )
}