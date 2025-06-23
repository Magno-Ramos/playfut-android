package com.magnus.playfut.domain.repository

import com.magnus.playfut.domain.database.daos.GroupDao
import com.magnus.playfut.domain.database.daos.PlayerDao
import com.magnus.playfut.domain.database.daos.RoundDao
import com.magnus.playfut.domain.model.structure.Player
import com.magnus.playfut.ui.features.statistic.home.StatisticHomeViewState

class StatisticsRepository(
    private val roundDao: RoundDao,
    private val groupDao: GroupDao,
    private val playerDao: PlayerDao
) {
    suspend fun fetchStatistics(groupId: String): Result<StatisticHomeViewState> = runCatching {
        val matches = groupDao.getMatchesCount(groupId.toLong())
        val totalGoals = groupDao.getTotalGoals(groupId.toLong())
        // val totalRounds = roundDao.getRoundCountByGroup(groupId)
        val artilleryRanking = playerDao.getPlayersScoreRanking(groupId)
        val playerMostPresent: Player? = null
        val playerWithMostWins: Player? = null
        StatisticHomeViewState(
            matches = matches,
            totalGoals = totalGoals,
            artilleryRanking = artilleryRanking,
            playerMostPresent = playerMostPresent,
            playerWithMostWins = playerWithMostWins
        )
    }
}