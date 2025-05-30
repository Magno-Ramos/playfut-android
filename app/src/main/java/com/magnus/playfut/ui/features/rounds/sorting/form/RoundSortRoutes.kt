package com.magnus.playfut.ui.features.rounds.sorting.form

sealed class RoundSortRoutes (val route: String) {
    object Form : RoundSortRoutes("round_sort_form_screen")
    object PlayerSelection : RoundSortRoutes("round_sort_player_selection_screen")
    object Result : RoundSortRoutes("round_sort_result")
}