package com.magnus.playfut.ui.domain.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.magnus.playfut.ui.domain.database.daos.GroupDao
import com.magnus.playfut.ui.domain.database.entities.Group

@Database(entities = [Group::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun groupDao(): GroupDao

    companion object {
        fun build(application: Application): AppDatabase {
            return Room.databaseBuilder(
                application,
                AppDatabase::class.java,
                "app-database"
            ).build()
        }
    }
}