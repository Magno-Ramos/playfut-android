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
        val totalActivePlayersCount = playerDao.getActivePlayersCount(groupId.toLong())
        val artilleryRanking = playerDao.getPlayersScoreRanking(groupId)

        var playerMostPresent: PlayerMostPresentHighlight? = null
        var playerWithMostWins: PlayerMostWinsHighlight? = null

        // rule to show player with most wins
        if (groupStats.totalRounds > 2 && totalActivePlayersCount >= 5) {
            val list = playerDao.getPlayersWithMostWins(groupId)
            val topPlayer = list.firstOrNull()
            if (topPlayer != null) {
                val hasGoodWinRate = topPlayer.winCount >= topPlayer.matchCount / 2
                val hasRelevantParticipation = topPlayer.matchCount >= groupStats.totalRounds / 2
                if (hasGoodWinRate && hasRelevantParticipation) {
                    playerWithMostWins = PlayerMostWinsHighlight(
                        player = topPlayer.player.name,
                        totalWins = topPlayer.winCount,
                        totalMatches = topPlayer.matchCount
                    )
                }
            }
        }

        // rule to show player most present
        if (groupStats.totalRounds > 2 && totalActivePlayersCount >= 5) {
            val list = playerDao.getMostPresentPlayers(groupId)
            val topPlayer = list.firstOrNull()
            if (topPlayer != null) {
                playerMostPresent = PlayerMostPresentHighlight(
                    player = topPlayer.player.name,
                    roundCount = topPlayer.roundCount,
                    totalRounds = groupStats.totalRounds
                )
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