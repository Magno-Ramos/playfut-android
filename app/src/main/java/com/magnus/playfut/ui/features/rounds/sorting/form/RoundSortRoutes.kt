package com.magnus.playfut.ui.features.rounds.sorting.form

sealed class RoundSortRoutes (val route: String) {
    data object Form : RoundSortRoutes("round_sort_form_screen")
    data object PlayerSelection : RoundSortRoutes("round_sort_player_selection_screen")
    data object FormConfirmation : RoundSortRoutes("round_sort_confirmation")
    data object Result : RoundSortRoutes("round_sort_result")
}