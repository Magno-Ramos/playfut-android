package com.magnus.playfut.ui.domain.repository.remote

import com.magnus.playfut.ui.domain.model.structure.Match
import com.magnus.playfut.ui.domain.repository.datasource.MatchDataSource

class RemoteMatchRepository : MatchDataSource {
    override suspend fun getMatchesFromRound(roundId: String): Result<List<Match>> {
        TODO("Not yet implemented")
    }
}