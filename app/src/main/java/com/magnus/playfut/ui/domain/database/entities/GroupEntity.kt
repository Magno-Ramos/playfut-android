package com.magnus.playfut.ui.domain.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Group(
    @PrimaryKey(autoGenerate = true) val id: String = "",
    val name: String = "",
    val players: List<Player> = listOf(),
    val rounds: List<Round> = listOf()
)

data class Round(
    val winner: String = "",
    val date: Date = Date()
)

data class Player(
    val name: String = "",
    val quality: Int = 0
)