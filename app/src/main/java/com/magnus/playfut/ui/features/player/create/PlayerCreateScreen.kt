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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.domain.model.PlayerType
import com.magnus.playfut.ui.domain.state.ActionResultState
import com.magnus.playfut.ui.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@Composable
fun PlayerCreateScreen(
    viewModel: PlayerCreateViewModel = koinViewModel(),
    groupId: String
) {
    val context = LocalContext.current
    val createState = viewModel.createPlayerResult.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    val text = remember { mutableStateOf("") }
    val (playerType, onPlayerTypeChange) = remember { mutableStateOf(PlayerType.UNIVERSAL) }
    var quality: Int by remember { mutableIntStateOf(3) }

    fun submitForm() {
        viewModel.createPlayer(
            groupId = groupId,
            name = text.value,
            type = playerType,
            quality = quality
        )
    }

    when (createState.value) {
        ActionResultState.Idle -> Unit
        ActionResultState.Loading -> Unit
        is ActionResultState.Success<*> -> {
            LaunchedEffect(Unit) {
                context.activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }

        is ActionResultState.Error -> {
            LaunchedEffect(Unit) {
                snackBarHostState.showSnackbar(message = "Desculpe, ocorreu um erro ao adicionar o jogador.")
            }
        }
    }

    Scaffold(
        containerColor = AppColor.bgPrimary,
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = AppColor.red,
                    contentColor = AppColor.white
                )
            }
        },
        topBar = {
            AppToolbar(
                title = "Adicionar Jogador",
                onClickBack = { context.activity?.onBackPressedDispatcher?.onBackPressed() }
            )
        },
        bottomBar = {
            BottomAppBar {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = createState.value != ActionResultState.Loading,
                    onClick = { submitForm() }
                ) {
                    Text(text = "Adicionar Jogador")
                }
            }
        }
    ) { paddings ->
        PlayerCreateForm(
            modifier = Modifier.padding(paddings),
            name = text.value,
            type = playerType,
            quality = quality,
            onNameChange = { text.value = it },
            onTypeChange = { onPlayerTypeChange(it) },
            onQualityChange = { quality = it }
        )
    }
}

@Composable
private fun PlayerCreateForm(
    modifier: Modifier = Modifier,
    name: String = "",
    type: PlayerType = PlayerType.UNIVERSAL,
    quality: Int = 3,
    onNameChange: (String) -> Unit = {},
    onQualityChange: (Int) -> Unit = {},
    onTypeChange: (PlayerType) -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }
    val typeOptions = PlayerType.entries.map { it.type }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Text(text = "Nome")
        Spacer(Modifier.height(4.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = name,
            onValueChange = { onNameChange(it) },
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
                            selected = (text == type.type),
                            onClick = { onTypeChange(PlayerType.fromType(text)) },
                            role = Role.RadioButton
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == type.type),
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
                value = quality.toFloat(),
                onValueChange = { onQualityChange(it.roundToInt()) },
                valueRange = 1f..5f,
                steps = 3,
                colors = SliderDefaults.colors(
                    thumbColor = AppColor.primary,
                    activeTrackColor = AppColor.primary,
                    inactiveTrackColor = AppColor.bgPrimary
                )
            )
            Text(
                text = quality.toString(),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview
@Composable
private fun PlayerCreateFormPreview() {
    AppTheme {
        Surface(Modifier.background(AppColor.bgPrimary)) {
            Column(Modifier.padding(16.dp)) {
                PlayerCreateForm()
            }
        }
    }
}