package com.magnus.playfut.domain.repository.local

import com.magnus.playfut.domain.database.daos.MatchDao
import com.magnus.playfut.domain.model.form.MatchForm
import com.magnus.playfut.domain.model.structure.Match
import com.magnus.playfut.domain.model.structure.toMatch
import com.magnus.playfut.domain.repository.datasource.MatchDataSource

class LocalMatchRepository(
    private val dao: MatchDao
) : MatchDataSource {
    override suspend fun getMatchesFromRound(roundId: String): Result<List<Match>> {
        return runCatching { dao.getMatchesForRound(roundId).map { it.toMatch() } }
    }

    override suspend fun pushMatch(form: MatchForm): Result<Long> {
        return runCatching { dao.insertMatchWithScores(form) }
    }
}