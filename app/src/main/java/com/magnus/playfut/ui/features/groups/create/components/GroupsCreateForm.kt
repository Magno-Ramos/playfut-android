package com.magnus.playfut.ui.features.groups.create.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.theme.AppColor

@Composable
fun GroupsCreateForm(
    onFormSubmit: (String) -> Unit,
    isLoading: Boolean = false
) {
    val focusRequester = remember { FocusRequester() }
    val text = remember { mutableStateOf("") }

    fun onClickSave() {
        if (text.value.isBlank()) return
        onFormSubmit(text.value)
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = text.value,
            onValueChange = { text.value = it },
            label = { Text("Nome do Grupo") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = AppColor.bgPrimary,
                focusedContainerColor = AppColor.white
            )
        )

        Button(
            enabled = !isLoading && text.value.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
            onClick = { onClickSave() },
            contentPadding = PaddingValues(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColor.primary,
                contentColor = AppColor.white,
                disabledContainerColor = AppColor.primary.copy(alpha = 0.5f),
                disabledContentColor = AppColor.white.copy(alpha = 0.5f)
            )
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )
                Spacer(Modifier.width(4.dp))
                if (isLoading) {
                    Text(text = "Criando grupo...")
                } else {
                    Text(text = "Salvar")
                }
            }
        }
    }
}