package com.magnus.playfut.ui.features.player.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.domain.model.structure.PlayerType
import com.magnus.playfut.ui.theme.AppTheme

@Composable
fun PlayerTypeInput(
    type: PlayerType = PlayerType.UNIVERSAL,
    onTypeChange: (PlayerType) -> Unit = {}
) {
    val typeOptions = PlayerType.entries.map { it.type }
    Column {
        Text(
            text = "Tipo",
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(4.dp))
        Column(
            modifier = Modifier
                .selectableGroup()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                .padding(16.dp, 4.dp)
        ) {
            typeOptions.forEach { text ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .selectable(
                            selected = (text == type.type),
                            onClick = { onTypeChange(PlayerType.fromType(text)) },
                            role = Role.RadioButton
                        )
                ) {
                    RadioButton(
                        selected = (text == type.type),
                        onClick = null
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 16.dp),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PlayerTypeInputPreview() {
    AppTheme {
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            PlayerTypeInput(
                type = PlayerType.UNIVERSAL,
                onTypeChange = {}
            )
        }
    }
}