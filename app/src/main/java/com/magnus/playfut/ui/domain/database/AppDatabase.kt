package com.magnus.playfut.ui.domain.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.magnus.playfut.ui.domain.database.converters.DateConverter
import com.magnus.playfut.ui.domain.database.converters.PlayerTypeConverter
import com.magnus.playfut.ui.domain.database.converters.SchemaPlayerRoleConverter
import com.magnus.playfut.ui.domain.database.daos.GroupDao
import com.magnus.playfut.ui.domain.database.daos.MatchDao
import com.magnus.playfut.ui.domain.database.daos.PlayerDao
import com.magnus.playfut.ui.domain.database.daos.RoundDao
import com.magnus.playfut.ui.domain.database.daos.ScoreDao
import com.magnus.playfut.ui.domain.database.daos.TeamDao
import com.magnus.playfut.ui.domain.database.entities.relations.SchemaPlayerCrossRef
import com.magnus.playfut.ui.domain.database.entities.relations.TeamRoundCrossRef
import com.magnus.playfut.ui.domain.database.entities.structure.GroupEntity
import com.magnus.playfut.ui.domain.database.entities.structure.MatchEntity
import com.magnus.playfut.ui.domain.database.entities.structure.PlayerEntity
import com.magnus.playfut.ui.domain.database.entities.structure.RoundEntity
import com.magnus.playfut.ui.domain.database.entities.structure.SchemaEntity
import com.magnus.playfut.ui.domain.database.entities.structure.ScoreEntity
import com.magnus.playfut.ui.domain.database.entities.structure.TeamEntity

@Database(
    entities = [
        GroupEntity::class,
        PlayerEntity::class,
        RoundEntity::class,
        TeamEntity::class,
        MatchEntity::class,
        ScoreEntity::class,
        SchemaEntity::class,
        SchemaPlayerCrossRef::class,
        TeamRoundCrossRef::class
    ], version = 1
)
@TypeConverters(DateConverter::class, PlayerTypeConverter::class, SchemaPlayerRoleConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun groupDao(): GroupDao

    abstract fun playerDao(): PlayerDao

    abstract fun roundDao(): RoundDao

    abstract fun matchDao(): MatchDao

    abstract fun scoreDao(): ScoreDao

    abstract fun teamDao(): TeamDao

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