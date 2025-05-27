package com.magnus.playfut.ui.features.groups.create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.magnus.playfut.ui.domain.state.ActionResultState
import com.magnus.playfut.ui.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.groups.create.components.GroupsCreateForm
import com.magnus.playfut.ui.features.groups.menu.GroupMenuActivity
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsCreateScreen(
    viewModel: GroupsCreateViewModel = koinViewModel(),
    onClickBack: () -> Unit = {}
) {
    val createGroupResultState = viewModel.createGroupResult.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    fun openMenu(groupId: String) {
        val intent = GroupMenuActivity.createIntent(context, groupId)
        context.startActivity(intent)
        context.activity?.finish()
    }

    LaunchedEffect(createGroupResultState.value) {
        when (createGroupResultState.value) {
            ActionResultState.Idle -> Unit
            ActionResultState.Loading -> {}
            is ActionResultState.Success<String> -> {
                val result = createGroupResultState.value as ActionResultState.Success<String>
                openMenu(result.data)
            }
            is ActionResultState.Error -> {
                snackBarHostState.showSnackbar(message = "Erro ao criar grupo")
            }
        }
    }

    AppTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) },
            containerColor = AppColor.bgPrimary,
            topBar = { AppToolbar(title = "Criar Grupo", onClickBack = onClickBack) }
        ) { paddings ->
            Box(modifier = Modifier.padding(paddings)) {
                GroupsCreateForm(
                    isLoading = createGroupResultState.value == ActionResultState.Loading,
                    onFormSubmit = { name ->
                        viewModel.createGroup(name)
                    }
                )
            }
        }
    }
}