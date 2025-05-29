package com.magnus.playfut.ui.features.groups.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.theme.AppTheme


@Composable
fun DeleteButton(
    isLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    val errorColor = MaterialTheme.colorScheme.error
    val onErrorColor = MaterialTheme.colorScheme.onError
    val backgroundColor = if (isLoading) errorColor.copy(alpha = 0.5f) else errorColor

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .clickable(enabled = !isLoading, onClick = onClick)
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = if (isLoading) onErrorColor.copy(alpha = 0.5f) else onErrorColor
        )
        Text(
            modifier = Modifier.weight(1f),
            text = if (!isLoading) "Excluir Grupo" else "Excluindo Grupo",
            color = if (isLoading) onErrorColor.copy(alpha = 0.5f) else onErrorColor,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )

        if (isLoading) {
            CircularProgressIndicator(
                trackColor = onErrorColor.copy(alpha = 0.5f),
                color = onErrorColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@PreviewLightDark
@Composable
fun GroupSettingsScreenPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DeleteButton()
            DeleteButton(isLoading = true)
        }
    }
}