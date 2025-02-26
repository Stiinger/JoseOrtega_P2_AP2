package edu.ucne.joseortega_p2_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.joseortega_p2_ap2.data.local.dao.Dao
import edu.ucne.joseortega_p2_ap2.data.local.entity.EntidadEntity

@Database(
    entities = [
        EntidadEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class JoseOrtegaP2Db : RoomDatabase() {
    abstract fun dao(): Dao
}