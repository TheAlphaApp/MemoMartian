package com.dzdexon.memomartian.ui.shared.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun CustomDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    primaryButtonText: String,
    primaryButtonEnabled: Boolean,
    onPrimaryButtonClick: () -> Unit,
    secondaryButtonText: String,
    onSecondaryButtonClick: () -> Unit,
    content: @Composable () -> Unit,
    ) {
    Dialog(
        onDismissRequest = onDismissRequest) {
        Card {
            Column(
                modifier = modifier
                    .padding(24.dp)
            ) {
                content()
                Spacer(modifier = Modifier.height(24.dp))
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onSecondaryButtonClick,
                        enabled = true,
                        modifier = Modifier
                    ) {
                        Text(secondaryButtonText)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = onPrimaryButtonClick,
                        enabled = primaryButtonEnabled,
                        modifier = Modifier
                    ) {
                        Text(primaryButtonText)
                    }
                }

            }
        }
    }
}