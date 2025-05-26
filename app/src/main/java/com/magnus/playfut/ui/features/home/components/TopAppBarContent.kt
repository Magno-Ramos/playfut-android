package com.magnus.playfut.ui.features.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.magnus.playfut.ui.theme.AppColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarContent(
    modifier: Modifier = Modifier,
    onClickNotification: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppColor.bgPrimary,
        ),
        title = {
            Text(
                text = "Seus Grupos",
                fontSize = TextUnit(value = 16f, type = TextUnitType.Sp),
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            IconButton(onClick = onClickNotification) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null
                )
            }
        }
    )
}