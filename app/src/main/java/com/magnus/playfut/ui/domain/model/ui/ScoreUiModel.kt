package com.magnus.playfut.ui.domain.model.ui

import com.magnus.playfut.ui.domain.database.entities.relations.PojoMatchWithScoresAndTeams
import com.magnus.playfut.ui.domain.database.entities.relations.PojoRoundWithDetails
import com.magnus.playfut.ui.domain.database.entities.structure.ScoreEntity

data class ScoreUiModel(
    val scorerName: String?, // Nome do jogador que marcou (você precisará buscar isso)
    val scoringTeamName: String?, // Nome do time que marcou (você precisará buscar isso)
    val isOwnGoal: Boolean,
    val description: String // Ex: "João (Time A) 23' " ou "Pedro (Time B) 45' (GC)"
)

data class MatchUiModel(
    val matchId: Long,
    val homeTeamName: String,
    val awayTeamName: String,
    val homeTeamScore: Int,
    val awayTeamScore: Int,
    val scores: List<ScoreUiModel> // Lista de gols formatados para a UI
    // Você pode adicionar outros campos que a UI precise, como status da partida (não iniciada, em andamento, finalizada)
    // val status: MatchStatus (um enum, por exemplo)
)

data class RoundDetailsUiModel(
    val roundId: Long,
    val roundDisplayName: String, // Ex: "Rodada 1", "Quartas de Final"
    val matches: List<MatchUiModel>,
    val participatingTeamNames: List<String> // Nomes dos times que participam na rodada, se aplicável
)

// Mapper para ScoreEntity -> ScoreUiModel
// Esta função é um exemplo e pode precisar buscar nomes de jogadores/times
// de um repositório ou tê-los já disponíveis se você expandir seus POJOs do Room.
fun ScoreEntity.toUiModel(
    // Idealmente, você teria acesso a um mapa de IDs para nomes ou buscaria isso
    // Aqui, vamos simular, você precisará adaptar para buscar os nomes reais.
    getPlayerName: (Long) -> String? = { id -> "Jogador $id" },
    getTeamName: (Long) -> String? = { id -> "Time $id" }
): ScoreUiModel {
    val scorerActualName = getPlayerName(this.playerId) ?: "Desconhecido"
    val teamActualName = getTeamName(this.teamIdScored) ?: "Time Desconhecido" // teamIdScored é o time que se beneficia

    val minuteDisplay = "" // Adicione lógica para o minuto se tiver o campo 'minute'
    val ownGoalDisplay = if (this.isOwnGoal) " (GC)" else ""

    return ScoreUiModel(
        scorerName = scorerActualName,
        scoringTeamName = teamActualName,
        isOwnGoal = this.isOwnGoal,
        description = "$scorerActualName$minuteDisplay$ownGoalDisplay"
    )
}

// Mapper para MatchWithScoresAndTeams -> MatchUiModel
fun PojoMatchWithScoresAndTeams.toUiModel(
    // Passando as funções de busca de nome para reutilização
    getPlayerName: (Long) -> String? = { id -> "Jogador $id" },
    getTeamName: (Long) -> String? = { id -> "Time $id" }
): MatchUiModel {
    // Se você não tem homeTeamScore/awayTeamScore na MatchEntity, calcule a partir dos scores:
    val calculatedHomeScore = this.scores.count { it.teamIdScored == this.match.homeTeamId }
    val calculatedAwayScore = this.scores.count { it.teamIdScored == this.match.awayTeamId }

    return MatchUiModel(
        matchId = this.match.id,
        homeTeamName = this.homeTeam?.name ?: "Desconhecido",
        awayTeamName = this.awayTeam?.name ?: "Desconhecido",
        homeTeamScore = calculatedHomeScore,
        awayTeamScore = calculatedAwayScore,
        scores = this.scores.map { it.toUiModel(getPlayerName, getTeamName) }
    )
}

// Mapper para PojoRoundWithDetails -> RoundDetailsUiModel
fun PojoRoundWithDetails.toUiModel(
    // Funções de busca de nome podem ser passadas do ViewModel/UseCase
    getPlayerName: (Long) -> String? = { id -> "Jogador $id" },
    getTeamName: (Long) -> String? = { id -> "Time $id" }
): RoundDetailsUiModel {
    return RoundDetailsUiModel(
        roundId = this.round.roundId,
        roundDisplayName = "Rodada ${this.round.roundId}",
        matches = this.matchesWithScoresAndTeams.map { it.toUiModel(getPlayerName, getTeamName) },
        participatingTeamNames = this.teamsInRound.map { it.name } // Supondo que TeamEntity tenha um campo 'name'
    )
}