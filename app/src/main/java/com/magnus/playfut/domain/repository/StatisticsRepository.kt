package com.magnus.playfut.domain.repository

import com.magnus.playfut.domain.database.daos.GroupDao
import com.magnus.playfut.domain.database.daos.PlayerDao
import com.magnus.playfut.domain.model.structure.Artillery
import com.magnus.playfut.ui.features.statistic.home.PlayerMostPresentHighlight
import com.magnus.playfut.ui.features.statistic.home.PlayerMostWinsHighlight
import com.magnus.playfut.ui.features.statistic.home.StatisticHomeViewState

class StatisticsRepository(
    private val groupDao: GroupDao,
    private val playerDao: PlayerDao
) {

    suspend fun fetchArtilleryRanking(groupId: String): Result<List<Artillery>> = runCatching {
        playerDao.getPlayersScoreRanking(groupId)
    }

    suspend fun fetchStatistics(groupId: String): Result<StatisticHomeViewState> = runCatching {
        val groupStats = groupDao.getGroupStats(groupId)
        val playerStats = playerDao.getPlayerStats(groupId)
        val totalActivePlayersCount = playerDao.getActivePlayersCount(groupId.toLong())
        val artilleryRanking = playerDao.getPlayersScoreRanking(groupId)

        var playerMostPresent: PlayerMostPresentHighlight? = null
        var playerWithMostWins: PlayerMostWinsHighlight? = null

        // rule to show player with most wins
        if (groupStats.totalRounds > 2 && totalActivePlayersCount >= 5) {
            val list = playerStats.sortedByDescending { it.wins }
            val topPlayer = list.firstOrNull()
            if (topPlayer != null) {
                val hasGoodWinRate = topPlayer.wins >= topPlayer.matches / 2
                val hasRelevantParticipation = topPlayer.matches >= groupStats.totalRounds / 2
                if (hasGoodWinRate && hasRelevantParticipation) {
                    val player = playerDao.getPlayerById(topPlayer.playerId)
                    playerWithMostWins = PlayerMostWinsHighlight(
                        player = player.name,
                        totalWins = topPlayer.wins,
                        totalMatches = groupStats.totalMatches
                    )
                }
            }
        }

        // rule to show player most present
        if (groupStats.totalRounds > 2 && totalActivePlayersCount >= 5) {
            val list = playerStats.sortedByDescending { it.rounds }
            val topPlayer = list.firstOrNull()
            if (topPlayer != null) {
                val player = playerDao.getPlayerById(topPlayer.playerId)
                val hasRelevantParticipation = topPlayer.matches >= groupStats.totalRounds / 2
                if (hasRelevantParticipation) {
                    playerMostPresent = PlayerMostPresentHighlight(
                        player = player.name,
                        roundCount = topPlayer.rounds,
                        totalRounds = groupStats.totalRounds
                    )
                }
            }
        }

        StatisticHomeViewState(
            totalRounds = groupStats.totalRounds,
            totalMatches = groupStats.totalMatches,
            totalGoals = groupStats.totalScores,
            artilleryRanking = artilleryRanking,
            playerMostPresent = playerMostPresent,
            playerWithMostWins = playerWithMostWins
        )
    }
}