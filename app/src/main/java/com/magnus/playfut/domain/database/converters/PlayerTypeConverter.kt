package com.magnus.playfut.domain.database.converters

import androidx.room.TypeConverter
import com.magnus.playfut.domain.model.structure.PlayerType

class PlayerTypeConverter {
    @TypeConverter
    fun fromPlayerType(value: PlayerType): String {
        return value.name
    }

    @TypeConverter
    fun toPlayerType(value: String): PlayerType {
        return PlayerType.valueOf(value)
    }
}