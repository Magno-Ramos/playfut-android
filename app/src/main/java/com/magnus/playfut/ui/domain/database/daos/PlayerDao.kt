package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.magnus.playfut.ui.domain.database.entities.structure.PlayerEntity

@Dao
interface PlayerDao {
    @Insert
    suspend fun insertPlayer(player: PlayerEntity)

    @Update
    suspend fun updatePlayer(player: PlayerEntity)

    @Delete
    suspend fun deletePlayer(player: PlayerEntity)

    @Query("SELECT * FROM players WHERE groupId = :groupId")
    suspend fun getPlayersByGroupId(groupId: Long): List<PlayerEntity>

    /**
     * Busca todos os jogadores de um time específico dentro de uma rodada específica.
     * Isso requer navegar Round -> Team -> Schema -> Players.
     *
     * Nota: Esta consulta assume que um time tem apenas UM esquema ativo por vez.
     * Se um time puder ter múltiplos esquemas e você precisar de um específico,
     * você precisaria de um critério adicional para selecionar o SchemaEntity correto.
     */
    @Transaction
    @Query("""
        SELECT P.*
        FROM Players P
        INNER JOIN SchemaPlayerCrossRef SP_CrossRef ON P.playerId = SP_CrossRef.playerId
        INNER JOIN Schemas S ON SP_CrossRef.schemaId = S.schemaId
        INNER JOIN Teams T ON S.teamId = T.teamId
        WHERE T.teamId = :targetTeamId AND T.roundId = :targetRoundId
    """)
    suspend fun getPlayersByTeamInRound(targetTeamId: String, targetRoundId: String): List<PlayerEntity>
}