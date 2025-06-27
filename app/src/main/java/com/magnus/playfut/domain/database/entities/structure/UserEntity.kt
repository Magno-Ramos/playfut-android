package com.magnus.playfut.domain.database.entities.structure

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class UserEntity(
    @PrimaryKey
    val uid: String,
    val isPro: Boolean
)