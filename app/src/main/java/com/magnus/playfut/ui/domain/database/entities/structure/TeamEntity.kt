package com.magnus.playfut.ui.domain.database.entities.structure

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.magnus.playfut.ui.domain.model.Team
import com.magnus.playfut.ui.domain.model.TeamSchema

@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey(autoGenerate = true)
    val teamId: Long = 0,
    val name: String
)

fun TeamEntity.toTeam() = Team(
    id = teamId.toString(),
    name = name,
    schema = TeamSchema(
        goalKeepers = emptyList(),
        startPlaying = emptyList(),
        substitutes = emptyList(),
        replacementSuggestions = emptyList()
    )
)