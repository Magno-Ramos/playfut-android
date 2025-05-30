package com.magnus.playfut.ui.features.rounds.sorting.form.state

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.magnus.playfut.ui.theme.AppTheme

@Composable
fun LoadingState() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@PreviewLightDark
@Composable
private fun LoadingStatePreview() {
    AppTheme {
        Column(Modifier.background(MaterialTheme.colorScheme.background)) {
            LoadingState()
        }
    }
}