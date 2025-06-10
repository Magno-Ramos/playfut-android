package com.magnus.playfut.ui.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.model.form.MatchForm
import com.magnus.playfut.ui.domain.model.structure.Match
import com.magnus.playfut.ui.domain.repository.datasource.MatchDataSource
import com.magnus.playfut.ui.domain.repository.local.LocalMatchRepository
import com.magnus.playfut.ui.domain.repository.remote.RemoteMatchRepository

class MatchRepository(
    private val localRepository: LocalMatchRepository,
    private val remoteRepository: RemoteMatchRepository,
    private val auth: FirebaseAuth
) : MatchDataSource {

    private val source
        get() = if (auth.currentUser != null) remoteRepository else localRepository

    override suspend fun getMatchesFromRound(roundId: String): Result<List<Match>> {
        return source.getMatchesFromRound(roundId)
    }

    override suspend fun pushMatch(form: MatchForm): Result<Long> {
        return source.pushMatch(form)
    }
}