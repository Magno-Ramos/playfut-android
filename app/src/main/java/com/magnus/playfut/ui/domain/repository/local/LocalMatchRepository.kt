package com.magnus.playfut.ui.domain.repository.local

import com.magnus.playfut.ui.domain.database.daos.MatchDao
import com.magnus.playfut.ui.domain.model.structure.Match
import com.magnus.playfut.ui.domain.model.structure.toMatch
import com.magnus.playfut.ui.domain.repository.datasource.MatchDataSource

class LocalMatchRepository(
    private val dao: MatchDao
) : MatchDataSource {
    override suspend fun getMatchesFromRound(roundId: String): Result<List<Match>> {
        return runCatching { dao.getMatchesForRound(roundId).map { it.toMatch() } }
    }
}