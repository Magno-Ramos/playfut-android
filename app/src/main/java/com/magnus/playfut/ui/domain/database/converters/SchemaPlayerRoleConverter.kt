package com.magnus.playfut.ui.domain.database.converters

import androidx.room.TypeConverter
import com.magnus.playfut.ui.domain.database.entities.relations.schema.SchemaPlayerRole

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