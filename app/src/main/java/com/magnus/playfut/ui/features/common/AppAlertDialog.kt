package com.magnus.playfut.ui.features.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun AppAlertDialog(
    dialogTitle: String,
    dialogText: String,
    confirmButtonText: String = "Confirm",
    dismissButtonText: String = "Dismiss",
    icon: ImageVector? = null,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    AlertDialog(
        icon = {
           if (icon != null) {
               Icon(icon, contentDescription = null)
           }
        },
        title = {
            Text(
                text = dialogTitle,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        },
        text = {
            Text(
                text = dialogText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
            )
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(
                    text = confirmButtonText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(
                    text = dismissButtonText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun AppAlertDialogPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.medium)
        ) {
            AppAlertDialog(
                onDismissRequest = {},
                onConfirmation = {},
                dialogTitle = "Alert dialog example",
                dialogText = "This is an example of an alert dialog with buttons."
            )
        }
    }
}