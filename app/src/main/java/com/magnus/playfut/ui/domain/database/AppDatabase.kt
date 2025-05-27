package com.magnus.playfut.ui.domain.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.magnus.playfut.ui.domain.database.converters.DateConverter
import com.magnus.playfut.ui.domain.database.daos.GroupDao
import com.magnus.playfut.ui.domain.database.entities.GroupEntity
import com.magnus.playfut.ui.domain.database.entities.PlayerEntity
import com.magnus.playfut.ui.domain.database.entities.RoundEntity

@Database(entities = [GroupEntity::class, PlayerEntity::class, RoundEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun groupDao(): GroupDao

    companion object {
        fun build(application: Application): AppDatabase {
            return Room.databaseBuilder(
                application,
                AppDatabase::class.java,
                "play-fut-database"
            ).build()
        }
    }
}