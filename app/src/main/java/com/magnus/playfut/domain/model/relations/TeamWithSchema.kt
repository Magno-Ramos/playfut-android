package com.magnus.playfut.domain.model.relations

import com.magnus.playfut.domain.database.entities.relations.crossref.SchemaPlayerRole
import com.magnus.playfut.domain.database.entities.relations.pojo.PojoPlayerWithRole
import com.magnus.playfut.domain.database.entities.relations.pojo.PojoSchemaWithPlayers
import com.magnus.playfut.domain.model.structure.Player
import com.magnus.playfut.domain.model.structure.toPlayer

class TeamWithSchema(
    val teamId: String,
    val teamName: String,
    val goalKeepers: List<Player>,
    val startPlaying: List<Player>,
    val substitutes: List<Player>
)

fun PojoSchemaWithPlayers.toTeamWithSchema(players: List<PojoPlayerWithRole>) = TeamWithSchema(
    teamId = schema.teamId.toString(),
    teamName = team.name,
    goalKeepers = players.filter { it.crossRef.role == SchemaPlayerRole.GOALKEEPER }.map { it.player.toPlayer() },
    startPlaying = players.filter { it.crossRef.role == SchemaPlayerRole.START_PLAYING }.map { it.player.toPlayer() },
    substitutes = players.filter { it.crossRef.role == SchemaPlayerRole.SUBSTITUTE }.map { it.player.toPlayer() }
)