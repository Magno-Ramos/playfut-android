package com.magnus.playfut.ui.features.groups.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.domain.state.ActionResultState
import com.magnus.playfut.ui.extensions.setLightStatusBar
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

class GroupSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        setLightStatusBar()
        super.onCreate(savedInstanceState)

        val groupId = intent.getStringExtra("group_id") ?: return finish()

        setContent {
            AppTheme {
                GroupSettingsScreen(groupId = groupId) {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
    }

    companion object {
        fun createIntent(context: Context, groupId: String): Intent {
            return Intent(context, GroupSettingsActivity::class.java).apply {
                putExtra("group_id", groupId)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GroupSettingsScreen(
    viewModel: GroupSettingsViewModel = koinViewModel(),
    groupId: String,
    onClickBack: () -> Unit = {}
) {
    val deleteGroupResultState = viewModel.deleteGroupResult.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(deleteGroupResultState.value) {
        when (deleteGroupResultState.value) {
            ActionResultState.Idle -> Unit
            ActionResultState.Loading -> Unit
            is ActionResultState.Success -> onClickBack()
            is ActionResultState.Error -> {
                snackBarHostState.showSnackbar(message = "Erro ao excluir o grupo")
            }
        }
    }

    Scaffold(
        containerColor = AppColor.bgPrimary,
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = { AppToolbar(title = "Configurações", onClickBack = onClickBack) }
    ) { paddings ->
        Column(
            modifier = Modifier
                .padding(paddings)
                .padding(16.dp)
        ) {
            DeleteButton(
                isLoading = deleteGroupResultState.value == ActionResultState.Loading,
                onClick = { viewModel.deleteGroup(groupId) }
            )
        }
    }
}

@Composable
fun DeleteButton(
    isLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    val backgroundColor = if (isLoading) AppColor.red.copy(alpha = 0.5f) else AppColor.red
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .clickable(enabled = !isLoading, onClick = onClick)
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = if (isLoading) AppColor.white.copy(alpha = 0.5f) else AppColor.white
        )
        Text(
            modifier = Modifier.weight(1f),
            text = if (!isLoading) "Excluir Grupo" else "Excluindo Grupo",
            color = if (isLoading) AppColor.white.copy(alpha = 0.5f) else AppColor.white,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )

        if (isLoading) {
            CircularProgressIndicator(
                trackColor = AppColor.white.copy(alpha = 0.5f),
                color = AppColor.white,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview
@Composable
fun GroupSettingsScreenPreview() {
    AppTheme {
        Surface {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                DeleteButton()
                DeleteButton(isLoading = true)
            }
        }
    }
}