package com.magnus.playfut.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SportsScore
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.theme.AppColor

@Composable
fun EmptyContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = Icons.Outlined.SportsScore, contentDescription = null)
        Text(
            text = "Você ainda não faz parte\n de nenhum grupo.",
            textAlign = TextAlign.Center,
            color = AppColor.primaryText,
            fontWeight = FontWeight.Bold
        )
    }
}