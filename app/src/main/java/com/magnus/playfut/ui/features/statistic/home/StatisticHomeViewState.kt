package com.magnus.playfut.ui.features.statistic.home

import com.magnus.playfut.domain.model.structure.Artillery

class StatisticHomeViewState(
    val matches: Int,
    val totalGoals: Int,
    val artilleryRanking: List<Artillery>
)