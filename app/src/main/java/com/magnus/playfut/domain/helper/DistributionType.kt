package com.magnus.playfut.domain.helper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.ui.graphics.vector.ImageVector

enum class DistributionType (
    val title: String,
    val icon: ImageVector,
) {
    RANDOM("Aleatório", Icons.Default.Shuffle),
    BALANCED_BY_RATING("Equilibrado", Icons.Default.Balance)
}