package com.magnus.playfut.ui.features.statistic.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.theme.spacing

@Composable
fun HomeLabel(text: String) {
    Text(
        modifier = Modifier.padding(bottom = MaterialTheme.spacing.small),
        text = text,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onBackground
    )
}