package com.dzdexon.memomartian.ui.shared.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import com.dzdexon.memomartian.ui.theme.LocalCustomColors


@Composable
fun NemoMinimalTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    value: String,
    textStyle: TextStyle,
    placeholderText: String,
    maxLines: Int = Int.MAX_VALUE
) {
    val colors = LocalCustomColors.current
    TextField(
        value = value,
        onValueChange = { it ->
            onValueChange(it)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        placeholder = { Text(placeholderText, style = textStyle) },
        modifier = modifier,
        minLines = 1,
        maxLines = maxLines,
        colors = TextFieldDefaults.colors(
            cursorColor = colors.onSecondary,
            focusedPlaceholderColor = colors.onTertiary,
            unfocusedPlaceholderColor = colors.onTertiary,
            focusedTextColor = colors.onPrimary,
            unfocusedTextColor = colors.onPrimary,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        textStyle = textStyle
    )
}
