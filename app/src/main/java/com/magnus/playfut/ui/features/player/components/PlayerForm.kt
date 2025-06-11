package com.magnus.playfut.ui.features.player.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.magnus.playfut.domain.model.structure.PlayerType
import com.magnus.playfut.ui.features.common.TextInput
import com.magnus.playfut.ui.theme.AppTheme

@Composable
fun PlayerForm(
    modifier: Modifier = Modifier,
    name: String = "",
    quality: Int = 3,
    type: PlayerType = PlayerType.UNIVERSAL,
    onNameChange: (String) -> Unit = {},
    onTypeChange: (PlayerType) -> Unit = {},
    onQualityChange: (Int) -> Unit = {},
    requestNameFocus: Boolean = false
) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scrollState), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        TextInput(
            label = "Nome",
            value = name,
            onChangeValue = { onNameChange(it) },
            requestFocus = requestNameFocus
        )
        PlayerTypeInput(type = type, onTypeChange = onTypeChange)
        PlayerQualityInput(quality = quality, onQualityChange = onQualityChange)
    }
}

@PreviewLightDark
@Composable
private fun PlayerFormPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            PlayerForm()
        }
    }
}