package com.magnus.playfut.ui.features.player.form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.magnus.playfut.domain.model.structure.Player
import com.magnus.playfut.domain.state.ActionResultState
import com.magnus.playfut.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.player.components.PlayerForm
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun PLayerEditScreen(
    viewModel: PlayerFormViewModel = koinViewModel(),
    player: Player
) {
    val context = LocalContext.current
    val editState = viewModel.editPlayerResult.collectAsStateWithLifecycle()
    val deleteState = viewModel.deletePlayerResult.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var playerName = remember { mutableStateOf(player.name) }
    var playerType = remember { mutableStateOf(player.position) }
    var playerQuality = remember { mutableIntStateOf(player.skillLevel) }

    fun closeScreen() {
        context.activity?.onBackPressedDispatcher?.onBackPressed()
    }

    fun showErrorSnack(message: String) = coroutineScope.launch {
        snackBarHostState.showSnackbar(message = message)
    }

    fun submitForm() {
        viewModel.editPlayer(
            id = player.id,
            groupId = player.groupId,
            name = playerName.value,
            type = playerType.value,
            quality = playerQuality.intValue
        )
    }

    fun removePlayer() {
        viewModel.removePlayer(player.id)
    }

    when (editState.value) {
        ActionResultState.Idle -> Unit
        ActionResultState.Loading -> Unit
        is ActionResultState.Success<*> -> closeScreen()
        is ActionResultState.Error -> showErrorSnack("Desculpe, ocorreu um erro")
    }

    when (deleteState.value) {
        ActionResultState.Idle -> Unit
        ActionResultState.Loading -> Unit
        is ActionResultState.Success<*> -> closeScreen()
        is ActionResultState.Error -> showErrorSnack("Desculpe, ocorreu um erro")
    }

    val isLoading = editState.value is ActionResultState.Loading ||
            deleteState.value is ActionResultState.Loading

    Scaffold(
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
                title = "Editar Jogador",
                onClickBack = { context.activity?.onBackPressedDispatcher?.onBackPressed() }
            )
        },
        bottomBar = {
            BottomEditPlayerBar(
                clickEnabled = !isLoading,
                onClickRemove = { removePlayer() },
                onClickSubmit = { submitForm() }
            )
        }
    ) { paddings ->
        PlayerForm(
            name = playerName.value,
            type = playerType.value,
            quality = playerQuality.intValue,
            onNameChange = { playerName.value = it },
            onTypeChange = { playerType.value = it },
            onQualityChange = { playerQuality.intValue = it },
            requestNameFocus = false,
            modifier = Modifier
                .padding(paddings)
                .padding(horizontal = 16.dp),
        )
    }
}

@Composable
fun BottomEditPlayerBar(
    modifier: Modifier = Modifier,
    onClickRemove: () -> Unit = {},
    onClickSubmit: () -> Unit = {},
    clickEnabled: Boolean = true
) {
    BottomAppBar(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f).height(48.dp),
                enabled = clickEnabled,
                onClick = { onClickRemove() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text(text = "Remover")
            }
            Button(
                modifier = Modifier.weight(1f).height(48.dp),
                enabled = clickEnabled,
                onClick = { onClickSubmit() }
            ) {
                Text(text = "Salvar")
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun BottomEditBarPreview() {
    AppTheme {
        Box(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            BottomEditPlayerBar()
        }
    }
}

