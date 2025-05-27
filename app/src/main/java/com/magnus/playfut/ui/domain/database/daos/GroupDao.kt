package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.magnus.playfut.ui.domain.model.Group

@Dao
interface GroupDao {
    @Query("SELECT * FROM `group`")
    fun getAll(): List<Group>

    @Insert
    fun insertAll(vararg groups: Group)

    @Delete
    fun delete(user: Group)
}