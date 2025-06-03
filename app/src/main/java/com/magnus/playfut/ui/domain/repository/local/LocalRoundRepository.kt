package com.magnus.playfut.ui.domain.repository.local

import com.magnus.playfut.ui.domain.database.daos.RoundDao
import com.magnus.playfut.ui.domain.datasource.RoundDataSource
import com.magnus.playfut.ui.domain.model.Round
import com.magnus.playfut.ui.domain.model.toRound

class LocalRoundRepository (
    private val dao: RoundDao
) : RoundDataSource {

    override suspend fun fetchRunningRound(groupId: String): Result<Round?> {
        return runCatching {
            dao.getOpenedRoundByGroup(groupId.toLong())?.toRound()
        }
    }

    override suspend fun fetchAllRounds(groupId: String): Result<List<Round>> {
        return runCatching {
            dao.getRoundsByGroup(groupId.toLong()).map { it.toRound() }
        }
    }
}