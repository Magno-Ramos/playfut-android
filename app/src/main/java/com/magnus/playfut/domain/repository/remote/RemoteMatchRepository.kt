package com.magnus.playfut.domain.repository.remote

import com.magnus.playfut.domain.model.form.MatchForm
import com.magnus.playfut.domain.model.structure.Match
import com.magnus.playfut.domain.repository.datasource.MatchDataSource

class RemoteMatchRepository : MatchDataSource {
    override suspend fun getMatchesFromRound(roundId: String): Result<List<Match>> {
        TODO("Not yet implemented")
    }

    override suspend fun pushMatch(form: MatchForm): Result<Long> {
        TODO("Not yet implemented")
    }
}