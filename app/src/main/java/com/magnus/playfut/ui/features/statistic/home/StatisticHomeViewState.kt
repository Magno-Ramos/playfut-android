package com.magnus.playfut.ui.features.statistic.home

import com.magnus.playfut.domain.model.structure.Artillery

class StatisticHomeViewState(
    val totalRounds: Int,
    val totalMatches: Int,
    val totalGoals: Int,
    val artilleryRanking: List<Artillery>,
    val playerWithMostWins: PlayerMostWinsHighlight? = null,
    val playerMostPresent: PlayerMostPresentHighlight? = null
)

class PlayerMostWinsHighlight(
    val player: String,
    val totalWins: Int,
    val totalMatches: Int
)

class PlayerMostPresentHighlight(
    val player: String,
    val roundCount: Int,
    val totalRounds: Int
)