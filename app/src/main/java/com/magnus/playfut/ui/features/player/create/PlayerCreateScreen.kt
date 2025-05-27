package com.magnus.playfut.ui.features.player.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.domain.model.PlayerType
import com.magnus.playfut.ui.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.theme.AppColor
import kotlin.math.roundToInt

@Composable
fun PlayerCreateScreen() {
    val context = LocalContext.current
    Scaffold(
        containerColor = AppColor.bgPrimary,
        topBar = {
            AppToolbar(
                title = "Adicionar Jogador",
                onClickBack = { context.activity?.onBackPressedDispatcher?.onBackPressed() }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.padding(16.dp),
                containerColor = AppColor.bgPrimary
            ) {
                Button(onClick = {}) {
                    Text(text = "Adicionar Jogador")
                }
            }
        }
    ) { paddings ->
        PlayerCreateForm(modifier = Modifier.padding(paddings))
    }
}

@Composable
private fun PlayerCreateForm(
    modifier: Modifier = Modifier,
    name: String = "",
    type: PlayerType = PlayerType.LINE,
    quality: Int = 3,
    onChangeName: (String) -> Unit = {},
    onChangeType: (PlayerType) -> Unit = {},
    onChangeQuality: (Int) -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    val text = remember { mutableStateOf("") }

    val typeOptions = PlayerType.entries.map { it.type }
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(typeOptions[0]) }
    var sliderValue by remember { mutableFloatStateOf(3f) }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Text(text = "Nome")
        Spacer(Modifier.height(4.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = text.value,
            onValueChange = { text.value = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = AppColor.white,
                focusedContainerColor = AppColor.white
            )
        )

        Spacer(Modifier.height(16.dp))
        Text(text = "Tipo")
        Spacer(Modifier.height(4.dp))
        Column(
            modifier = Modifier
                .selectableGroup()
                .background(AppColor.white, RoundedCornerShape(8.dp))
                .padding(16.dp, 8.dp)
        ) {
            typeOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) },
                            role = Role.RadioButton
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = null
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Text(text = "Qualidade")
        Spacer(Modifier.height(4.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(AppColor.white, RoundedCornerShape(8.dp))
                .padding(16.dp, 8.dp)
        ) {
            Slider(
                modifier = Modifier.weight(1f),
                value = sliderValue,
                onValueChange = { sliderValue = it.roundToInt().toFloat() },
                valueRange = 1f..5f,
                steps = 3,
                colors = SliderDefaults.colors(
                    thumbColor = AppColor.primary,
                    activeTrackColor = AppColor.primary,
                    inactiveTrackColor = AppColor.bgPrimary
                )
            )
            Text(
                text = sliderValue.toInt().toString(),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}