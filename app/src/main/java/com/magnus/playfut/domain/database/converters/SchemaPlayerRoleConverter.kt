package com.magnus.playfut.domain.database.converters

import androidx.room.TypeConverter
import com.magnus.playfut.domain.database.entities.relations.crossref.SchemaPlayerRole

class SchemaPlayerRoleConverter {
    @TypeConverter
    fun fromRole(role: SchemaPlayerRole): String {
        return role.name
    }

    @TypeConverter
    fun toJson(role: String): SchemaPlayerRole {
        return SchemaPlayerRole.valueOf(role)
    }
}