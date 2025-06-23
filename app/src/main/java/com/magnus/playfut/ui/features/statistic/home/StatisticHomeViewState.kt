package com.magnus.playfut.ui.features.statistic.home

import com.magnus.playfut.domain.model.structure.Artillery
import com.magnus.playfut.domain.model.structure.Player

class StatisticHomeViewState(
    val matches: Int,
    val totalGoals: Int,
    val artilleryRanking: List<Artillery>,
    val playerWithMostWins: Player? = null,
    val playerMostPresent: Player? = null
)