package com.magnus.playfut.ui.features.rounds.playing

sealed class RoundPlayingRoutes(val route: String) {
    data object Home : RoundPlayingRoutes("round_playing_home_screen")
    data object Match : RoundPlayingRoutes("round_playing_match_screen")
    data object MatchSelection : RoundPlayingRoutes("round_playing_match_selection_screen")
    object TeamDetail : RoundPlayingRoutes("round_playing_team_detail_screen/{teamId}") {
        fun createRoute(teamId: String): String {
            return "round_playing_team_detail_screen/$teamId"
        }
    }
}