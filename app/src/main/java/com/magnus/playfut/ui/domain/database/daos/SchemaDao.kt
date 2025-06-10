package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.magnus.playfut.ui.domain.database.entities.structure.SchemaEntity

@Dao
interface SchemaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(schema: SchemaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(schemas: List<SchemaEntity>)
}