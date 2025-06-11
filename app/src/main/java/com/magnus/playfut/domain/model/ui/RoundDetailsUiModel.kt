package com.magnus.playfut.domain.model.ui

data class RoundDetailsUiModel(
    val roundId: Long,
    val roundDisplayName: String,
    val matches: List<MatchUiModel>,
    val teams: List<TeamUiModel>,
)