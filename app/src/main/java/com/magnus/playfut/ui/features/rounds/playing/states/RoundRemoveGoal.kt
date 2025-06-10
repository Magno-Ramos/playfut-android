package com.magnus.playfut.ui.features.rounds.playing.states

data class RoundRemoveGoal (
    val isVisible: Boolean,
    var scoreItem: RoundScoreItem? = null
)