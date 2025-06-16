package com.magnus.playfut.domain.database.converters

import androidx.room.TypeConverter
import com.magnus.playfut.domain.model.structure.PlayerPosition

class PlayerPositionConverter {
    @TypeConverter
    fun fromPlayerType(value: PlayerPosition): String {
        return value.name
    }

    @TypeConverter
    fun toPlayerType(value: String): PlayerPosition {
        return PlayerPosition.valueOf(value)
    }
}