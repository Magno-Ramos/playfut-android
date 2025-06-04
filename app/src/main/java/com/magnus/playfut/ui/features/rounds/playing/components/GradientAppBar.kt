package com.magnus.playfut.ui.features.rounds.playing.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradientAppBar(
    title: String = "",
    onClickBack: () -> Unit = {}
) {
    val gradient = Brush.verticalGradient(
        listOf(
            AppColor.green,
            MaterialTheme.colorScheme.background
        )
    )
    Box(
        modifier = Modifier.background(gradient)
    ) {
        AppToolbar(
            title = title,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            onClickBack = onClickBack
        )
    }
}

@Preview(
    showSystemUi = true,
    device = "id:medium_phone",
    apiLevel = 36
)
@Preview(
    showSystemUi = true,
    device = "id:medium_phone",
    apiLevel = 36,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun GradientAppBarPreview() {
    AppTheme {
        Scaffold(
            topBar = { GradientAppBar() }
        ) { paddings -> Box(Modifier.padding(paddings)) }
    }
}