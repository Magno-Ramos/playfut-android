package com.magnus.playfut.domain.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.magnus.playfut.domain.database.converters.DateConverter
import com.magnus.playfut.domain.database.converters.PlayerPositionConverter
import com.magnus.playfut.domain.database.converters.PlayerTypeConverter
import com.magnus.playfut.domain.database.converters.SchemaPlayerRoleConverter
import com.magnus.playfut.domain.database.daos.GroupDao
import com.magnus.playfut.domain.database.daos.MatchDao
import com.magnus.playfut.domain.database.daos.PlayerDao
import com.magnus.playfut.domain.database.daos.RoundDao
import com.magnus.playfut.domain.database.daos.SchemaDao
import com.magnus.playfut.domain.database.daos.ScoreDao
import com.magnus.playfut.domain.database.daos.TeamDao
import com.magnus.playfut.domain.database.entities.relations.crossref.CrossRefSchemaPlayer
import com.magnus.playfut.domain.database.entities.relations.crossref.CrossRefTeamRound
import com.magnus.playfut.domain.database.entities.structure.GroupEntity
import com.magnus.playfut.domain.database.entities.structure.MatchEntity
import com.magnus.playfut.domain.database.entities.structure.PlayerEntity
import com.magnus.playfut.domain.database.entities.structure.RoundEntity
import com.magnus.playfut.domain.database.entities.structure.RoundResultEntity
import com.magnus.playfut.domain.database.entities.structure.SchemaEntity
import com.magnus.playfut.domain.database.entities.structure.ScoreEntity
import com.magnus.playfut.domain.database.entities.structure.TeamEntity

@Database(
    entities = [
        GroupEntity::class,
        PlayerEntity::class,
        RoundEntity::class,
        TeamEntity::class,
        MatchEntity::class,
        ScoreEntity::class,
        SchemaEntity::class,
        RoundResultEntity::class,
        CrossRefSchemaPlayer::class,
        CrossRefTeamRound::class
    ], version = 1
)
@TypeConverters(
    DateConverter::class,
    PlayerPositionConverter::class,
    SchemaPlayerRoleConverter::class,
    PlayerTypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun groupDao(): GroupDao

    abstract fun playerDao(): PlayerDao

    abstract fun roundDao(): RoundDao

    abstract fun matchDao(): MatchDao

    abstract fun scoreDao(): ScoreDao

    abstract fun teamDao(): TeamDao

    abstract fun schemaDao(): SchemaDao

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