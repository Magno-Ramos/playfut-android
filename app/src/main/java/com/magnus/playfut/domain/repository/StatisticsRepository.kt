package com.magnus.playfut.domain.repository

import com.magnus.playfut.domain.database.daos.GroupDao
import com.magnus.playfut.domain.database.daos.PlayerDao
import com.magnus.playfut.domain.database.daos.RoundDao
import com.magnus.playfut.ui.features.statistic.home.PlayerMostPresentHighlight
import com.magnus.playfut.ui.features.statistic.home.PlayerMostWinsHighlight
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
        var playerMostPresent: PlayerMostPresentHighlight? = null
        var playerWithMostWins: PlayerMostWinsHighlight? = null

        if (totalRounds > 2) {
            val pojo = playerDao.getPlayerWithMostWins(groupId)
            playerWithMostWins = PlayerMostWinsHighlight(
                player = pojo.player.name,
                totalWins = pojo.winCount,
                totalMatches = pojo.matchCount
            )
        }

        if (totalRounds > 4) {
            val pojo = playerDao.getMostPresentPlayer(groupId)
            playerMostPresent = PlayerMostPresentHighlight(
                player = pojo.player.name,
                roundCount = pojo.roundCount,
                totalRounds = totalRounds
            )
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