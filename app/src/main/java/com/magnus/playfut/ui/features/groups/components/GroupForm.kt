package com.magnus.playfut.ui.features.groups.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.features.common.TextInput

@Composable
fun GroupForm(
    name: String,
    onNameChange: (String) -> Unit,
    requestFocus: Boolean = false
) {
    Column (Modifier.padding(16.dp)) {
        TextInput(
            label = "Nome do Grupo",
            value = name,
            onChangeValue = { onNameChange(it) },
            requestFocus = requestFocus
        )
    }
}