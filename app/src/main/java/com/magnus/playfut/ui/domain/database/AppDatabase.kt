package com.magnus.playfut.ui.domain.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.magnus.playfut.ui.domain.database.converters.DateConverter
import com.magnus.playfut.ui.domain.database.converters.PlayerTypeConverter
import com.magnus.playfut.ui.domain.database.daos.GoalDao
import com.magnus.playfut.ui.domain.database.daos.GroupDao
import com.magnus.playfut.ui.domain.database.daos.PlayerDao
import com.magnus.playfut.ui.domain.database.daos.PlayerInTeamInRoundDao
import com.magnus.playfut.ui.domain.database.daos.RoundDao
import com.magnus.playfut.ui.domain.database.daos.RoundResultDao
import com.magnus.playfut.ui.domain.database.daos.SubstitutionDao
import com.magnus.playfut.ui.domain.database.daos.TeamDao
import com.magnus.playfut.ui.domain.database.daos.TeamInRoundDao
import com.magnus.playfut.ui.domain.database.daos.TeamInRoundWithDetailsDao
import com.magnus.playfut.ui.domain.database.daos.TeamInRoundWithFullDataDao
import com.magnus.playfut.ui.domain.database.entities.GoalEntity
import com.magnus.playfut.ui.domain.database.entities.GroupEntity
import com.magnus.playfut.ui.domain.database.entities.PlayerEntity
import com.magnus.playfut.ui.domain.database.entities.PlayerInTeamInRoundEntity
import com.magnus.playfut.ui.domain.database.entities.RoundEntity
import com.magnus.playfut.ui.domain.database.entities.RoundResultEntity
import com.magnus.playfut.ui.domain.database.entities.SubstitutionEntryEntity
import com.magnus.playfut.ui.domain.database.entities.TeamEntity
import com.magnus.playfut.ui.domain.database.entities.TeamInRoundEntity

@Database(
    entities = [
        GroupEntity::class,
        PlayerEntity::class,
        RoundEntity::class,
        TeamEntity::class,
        RoundResultEntity::class,
        SubstitutionEntryEntity::class,
        GoalEntity::class,
        PlayerInTeamInRoundEntity::class,
        TeamInRoundEntity::class
    ], version = 1
)
@TypeConverters(DateConverter::class, PlayerTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun groupDao(): GroupDao

    abstract fun playerDao(): PlayerDao

    abstract fun teamDao(): TeamDao

    abstract fun roundDao(): RoundDao

    abstract fun roundResultDao(): RoundResultDao

    abstract fun substitutionDao(): SubstitutionDao

    abstract fun goalDao(): GoalDao

    abstract fun playerInTeamInRoundDao(): PlayerInTeamInRoundDao

    abstract fun teamInRoundDao(): TeamInRoundDao

    abstract fun teamInRoundWithDetailsDao(): TeamInRoundWithDetailsDao

    abstract fun teamInRoundWithFullDataDao(): TeamInRoundWithFullDataDao

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