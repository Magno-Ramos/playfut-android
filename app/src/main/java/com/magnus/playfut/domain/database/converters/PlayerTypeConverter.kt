package com.magnus.playfut.domain.database.converters

import androidx.room.TypeConverter
import com.magnus.playfut.domain.model.structure.PlayerType

class PlayerTypeConverter {
    @TypeConverter
    fun fromRole(type: PlayerType): String {
        return type.name
    }

    @TypeConverter
    fun toJson(type: PlayerType): PlayerType {
        return PlayerType.entries.first { it.name == type.name }
    }
}