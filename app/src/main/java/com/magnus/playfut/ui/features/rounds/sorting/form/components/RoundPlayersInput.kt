package com.magnus.playfut.ui.features.rounds.sorting.form.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme

@Composable
fun RoundPlayersInput(
    modifier: Modifier = Modifier,
    totalPlayers: String,
    onClickChange: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = { onClickChange() })
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = buildAnnotatedString {
                append("Jogadores presentes: ")
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                append(totalPlayers)
            },
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp
        )
        Text(
            text = "Alterar",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp),
            color = AppColor.blue,
            fontSize = 14.sp
        )
    }
}

@PreviewLightDark
@Composable
private fun RoundPlayersInputPreview() {
    AppTheme {
        Column(Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            RoundPlayersInput(totalPlayers = "12")
        }
    }
}