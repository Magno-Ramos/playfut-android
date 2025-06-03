package com.magnus.playfut.ui.features.rounds.playing

sealed class RoundPlayingRoutes(val route: String) {
    data object Home : RoundPlayingRoutes("round_playing_home_screen")
}