package com.magnus.playfut.ui.features.groups.form

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.domain.state.ActionResultState
import com.magnus.playfut.ui.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.groups.components.GroupForm
import com.magnus.playfut.ui.features.groups.menu.GroupMenuActivity
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsCreateScreen(
    viewModel: GroupsFormViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val createGroupResultState = viewModel.createGroupResult.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val name = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    fun closeScreen() {
        context.activity?.finish()
    }

    fun openMenu(groupId: String) {
        val intent = GroupMenuActivity.createIntent(context, groupId)
        context.startActivity(intent)
        context.activity?.finish()
    }

    fun showErrorSnackBar(message: String) {
        coroutineScope.launch {
            snackBarHostState.showSnackbar(message = message)
        }
    }

    when (val state = createGroupResultState.value) {
        ActionResultState.Idle -> Unit
        ActionResultState.Loading -> {}
        is ActionResultState.Success<String> -> openMenu(state.data)
        is ActionResultState.Error -> showErrorSnackBar("Erro ao criar grupo")
    }

    val isButtonEnabled = (createGroupResultState.value != ActionResultState.Loading && name.value.isNotBlank())

    AppTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) },
            containerColor = AppColor.bgPrimary,
            topBar = {
                AppToolbar(
                    title = "Criar Grupo",
                    onClickBack = { closeScreen() }
                )
            },
            bottomBar = {
                AppBottomBar(
                    isButtonEnabled = isButtonEnabled,
                    onClickCreateGroup = { viewModel.createGroup(name.value) }
                )
            }
        ) { paddings ->
            Box(modifier = Modifier.padding(paddings)) {
                GroupForm(
                    name = name.value,
                    onNameChange = { name.value = it },
                    requestFocus = true
                )
            }
        }
    }
}

@Composable
private fun AppBottomBar(
    modifier: Modifier = Modifier,
    isButtonEnabled: Boolean,
    onClickCreateGroup: () -> Unit,
) {
    BottomAppBar(
        modifier = modifier,
        containerColor = AppColor.bgPrimary,
        contentPadding = PaddingValues(16.dp)
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = isButtonEnabled,
            onClick = { onClickCreateGroup() }
        ) {
            Text(text = "Criar Grupo")
        }
    }
}