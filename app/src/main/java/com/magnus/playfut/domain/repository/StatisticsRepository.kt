package com.magnus.playfut.domain.repository

import com.magnus.playfut.domain.database.daos.GroupDao
import com.magnus.playfut.domain.database.daos.PlayerDao
import com.magnus.playfut.ui.features.statistic.home.StatisticHomeViewState

class StatisticsRepository(
    private val groupDao: GroupDao,
    private val playerDao: PlayerDao
) {
    suspend fun fetchStatistics(groupId: String): Result<StatisticHomeViewState> = runCatching {
        val matches = groupDao.getMatchesCount(groupId.toLong())
        val totalGoals = groupDao.getTotalGoals(groupId.toLong())
        val artilleryRanking = playerDao.getPlayersScoreRanking(groupId)
        StatisticHomeViewState(matches, totalGoals, artilleryRanking)
    }
}