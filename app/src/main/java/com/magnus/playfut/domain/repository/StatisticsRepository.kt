package com.magnus.playfut.domain.repository

import com.magnus.playfut.domain.database.daos.GroupDao
import com.magnus.playfut.domain.database.daos.PlayerDao
import com.magnus.playfut.domain.database.daos.RoundDao
import com.magnus.playfut.domain.model.structure.Artillery
import com.magnus.playfut.ui.features.statistic.home.PlayerMostPresentHighlight
import com.magnus.playfut.ui.features.statistic.home.PlayerMostWinsHighlight
import com.magnus.playfut.ui.features.statistic.home.StatisticHomeViewState

class StatisticsRepository(
    private val roundDao: RoundDao,
    private val groupDao: GroupDao,
    private val playerDao: PlayerDao
) {

    suspend fun fetchArtilleryRanking(groupId: String): Result<List<Artillery>> = runCatching {
        playerDao.getPlayersScoreRanking(groupId)
    }

    suspend fun fetchStatistics(groupId: String): Result<StatisticHomeViewState> = runCatching {
        val totalActivePlayersCount = playerDao.getActivePlayersCount(groupId.toLong())
        val totalRounds = roundDao.getRoundCountByGroup(groupId)
        val totalMatches = groupDao.getMatchesCount(groupId.toLong())
        val totalGoals = groupDao.getTotalGoals(groupId.toLong())
        val artilleryRanking = playerDao.getPlayersScoreRanking(groupId)

        var playerMostPresent: PlayerMostPresentHighlight? = null
        var playerWithMostWins: PlayerMostWinsHighlight? = null

        // rule to show player with most wins
        if (totalRounds > 2 && totalActivePlayersCount >= 5) {
            val list = playerDao.getPlayersWithMostWins(groupId)
            val topPlayer = list.firstOrNull()
            if (topPlayer != null) {
                val hasGoodWinRate = topPlayer.winCount >= topPlayer.matchCount / 2
                val hasRelevantParticipation = topPlayer.matchCount >= totalRounds / 2
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
        if (totalRounds > 2 && totalActivePlayersCount >= 5) {
            val list = playerDao.getMostPresentPlayers(groupId)
            val topPlayer = list.firstOrNull()
            if (topPlayer != null) {
                playerMostPresent = PlayerMostPresentHighlight(
                    player = topPlayer.player.name,
                    roundCount = topPlayer.roundCount,
                    totalRounds = totalRounds
                )
            }
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