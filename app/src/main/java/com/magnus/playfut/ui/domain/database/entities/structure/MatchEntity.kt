package com.magnus.playfut.ui.domain.database.entities.structure

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = RoundEntity::class,
            parentColumns = ["roundId"],      // Coluna 'roundId' em RoundEntity
            childColumns = ["roundId"],       // Coluna 'roundId' em MatchEntity
            onDelete = ForeignKey.CASCADE,   // Ou RESTRICT, CASCADE, dependendo da sua lógica
            onUpdate = ForeignKey.CASCADE     // Opcional: o que fazer se o roundId na RoundEntity mudar
        )
    ],
    indices = [
        Index(value = ["roundId"])
    ]
)
data class MatchEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val homeTeamId: Long,
    val awayTeamId: Long,
    val roundId: Long
)