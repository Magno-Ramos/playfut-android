package com.magnus.playfut.ui.domain.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.domain.model.Player
import com.magnus.playfut.ui.domain.model.PlayerType
import com.magnus.playfut.ui.domain.model.Round
import java.util.Date

@Entity(tableName = "groups")
data class GroupEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String = "",
    val createdAt: Date = Date()
)

@Entity(
    tableName = "players",
    foreignKeys = [ForeignKey(
        entity = GroupEntity::class,
        parentColumns = ["id"],
        childColumns = ["groupId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String = "",
    val skillLevel: Int = 0,
    val groupId: Long = 0,
    val type: PlayerType = PlayerType.UNIVERSAL
)

data class GroupWithPlayersAndRounds(
    @Embedded val group: GroupEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val players: List<PlayerEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val rounds: List<RoundEntity>
)

fun PlayerEntity.toPlayer() = Player(
    id = id.toString(),
    groupId = groupId.toString(),
    name = name,
    skillLevel = skillLevel,
    type = type
)

fun RoundEntity.toRound() = Round(
    date = date,
    teams = listOf()
)

fun GroupWithPlayersAndRounds.toGroup() = Group(
    id = group.id.toString(),
    name = group.name,
    players = players.map { it.toPlayer() },
    rounds = rounds.map { it.toRound() }
)