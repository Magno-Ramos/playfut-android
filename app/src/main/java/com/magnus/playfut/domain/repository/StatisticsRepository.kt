package com.magnus.playfut.domain.repository

import com.magnus.playfut.domain.database.daos.GroupDao
import com.magnus.playfut.domain.database.daos.PlayerDao
import com.magnus.playfut.domain.database.daos.RoundDao
import com.magnus.playfut.domain.model.structure.Player
import com.magnus.playfut.domain.model.structure.toPlayer
import com.magnus.playfut.ui.features.statistic.home.StatisticHomeViewState

class StatisticsRepository(
    private val roundDao: RoundDao,
    private val groupDao: GroupDao,
    private val playerDao: PlayerDao
) {
    suspend fun fetchStatistics(groupId: String): Result<StatisticHomeViewState> = runCatching {
        val totalRounds = roundDao.getRoundCountByGroup(groupId)
        val totalMatches = groupDao.getMatchesCount(groupId.toLong())
        val totalGoals = groupDao.getTotalGoals(groupId.toLong())
        val artilleryRanking = playerDao.getPlayersScoreRanking(groupId)
        var playerMostPresent: Player? = null
        var playerWithMostWins: Player? = null

        if (totalRounds > 2) {
            playerWithMostWins = playerDao.getPlayerWithMostWins().toPlayer()
        }

        if (totalRounds > 4) {
            playerMostPresent = playerDao.getMostPresentPlayer().toPlayer()
        }

        StatisticHomeViewState(
            totalRounds = totalRounds,
            totalMatches = totalMatches,
            totalGoals = totalGoals,
            artilleryRanking = artilleryRanking,
            playerMostPresent = playerMostPresent,
            playerWithMostWins = playerWithMostWins
        )
    }
}