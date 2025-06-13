package com.magnus.playfut.ui.features.rounds.playing.states

import java.util.Date

sealed class RoundPlayingResultViewState(
    val groupName: String,
    val date: Date,
) {
    class Draw(
        groupName: String,
        date: Date,
        val teams: List<String>
    ) : RoundPlayingResultViewState(
        groupName,
        date
    )

    class Victory(
        groupName: String,
        date: Date,
        val teamName: String,
        val wins: Int,
        val draws: Int,
        val losses: Int,
        val goalsScored: Int,
        val goalsConceded: Int
    ) : RoundPlayingResultViewState(
        groupName,
        date
    )
}

