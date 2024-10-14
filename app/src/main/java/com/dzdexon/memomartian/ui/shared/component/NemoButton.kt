package com.dzdexon.memomartian.ui.shared.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dzdexon.memomartian.ui.theme.LocalCustomColors

@Composable
fun NemoButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val colors = LocalCustomColors.current
    Button(
        modifier = modifier.padding(0.dp),
        colors = ButtonColors(
            contentColor = colors.onSecondary,
            containerColor = colors.secondary,
            disabledContentColor = colors.onTertiary,
            disabledContainerColor = colors.onTertiary
        ),
        onClick = {
            onClick()
        }) {
        content()
    }
}