package com.magnus.playfut.ui.features.rounds.playing.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun GoalCelebration(
    isPlaying: Boolean,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spec = LottieCompositionSpec.Asset("goal_animation.json")
    val composition by rememberLottieComposition(spec)
    var wasPlaying by remember { mutableStateOf(false) }

    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying,
        iterations = 1,
        speed = 2f,
    )

    LaunchedEffect(progress, isPlaying) {
        if (isPlaying && wasPlaying && progress >= 1f) {
            onFinished()
        }

        wasPlaying = isPlaying
    }

    if (isPlaying) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = modifier.size(200.dp),
        )
    }
}