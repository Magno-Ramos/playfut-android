package com.magnus.playfut.ui.features.player.form

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.domain.model.PlayerType
import com.magnus.playfut.ui.domain.state.ActionResultState
import com.magnus.playfut.ui.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.player.components.PlayerForm
import com.magnus.playfut.ui.theme.AppColor
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlayerCreateScreen(
    viewModel: PlayerFormViewModel = koinViewModel(),
    groupId: String
) {
    val context = LocalContext.current
    val createState = viewModel.createPlayerResult.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var playerName = remember { mutableStateOf("") }
    var playerType = remember { mutableStateOf(PlayerType.UNIVERSAL) }
    var playerQuality = remember { mutableIntStateOf(3) }

    fun closeScreen() {
        context.activity?.onBackPressedDispatcher?.onBackPressed()
    }

    fun showErrorSnack(message: String) = coroutineScope.launch {
        snackBarHostState.showSnackbar(message = message)
    }

    fun submitForm() {
        viewModel.createPlayer(
            groupId = groupId,
            name = playerName.value,
            type = playerType.value,
            quality = playerQuality.intValue
        )
    }

    when (createState.value) {
        ActionResultState.Idle -> Unit
        ActionResultState.Loading -> Unit
        is ActionResultState.Success<*> -> closeScreen()
        is ActionResultState.Error -> showErrorSnack("Desculpe, ocorreu um erro ao adicionar o jogador.")
    }

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
                title = "Adicionar Jogador",
                onClickBack = { context.activity?.onBackPressedDispatcher?.onBackPressed() }
            )
        },
        bottomBar = {
            BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
                Button(
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    enabled = createState.value != ActionResultState.Loading,
                    onClick = { submitForm() }
                ) {
                    Text(text = "Adicionar Jogador")
                }
            }
        }
    ) { paddings ->
        PlayerForm(
            modifier = Modifier
                .padding(paddings)
                .padding(horizontal = 16.dp),
            name = playerName.value,
            type = playerType.value,
            quality = playerQuality.intValue,
            onNameChange = { playerName.value = it },
            onTypeChange = { playerType.value = it },
            onQualityChange = { playerQuality.intValue = it },
            requestNameFocus = true
        )
    }
}