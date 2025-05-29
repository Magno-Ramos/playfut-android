package com.magnus.playfut.ui.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.theme.AppColor

@Composable
fun CreateGroupButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(all = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColor.white,
            contentColor = AppColor.primaryText,
            disabledContainerColor = AppColor.bgSecondary,
            disabledContentColor = AppColor.tertiaryText
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Outlined.AddCircle, contentDescription = null, tint = AppColor.green)
            Text(modifier = Modifier.weight(1f), text = "Criar Novo Grupo", fontWeight = FontWeight.Medium)
        }
    }
}