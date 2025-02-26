package edu.ucne.joseortega_p2_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.joseortega_p2_ap2.data.local.entity.EntidadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Upsert()
    suspend fun save(entidades: List<EntidadEntity>)
    @Query(
        """
            SELECT *FROM Entidades WHERE entidadId == :id limit 1
        """
    )
    suspend fun find(id: Int): EntidadEntity?
    @Delete
    suspend fun delete(entidad: EntidadEntity)
    @Query("SELECT * FROM Entidades")
    fun getAll(): Flow<List<EntidadEntity>>
}