package edu.ucne.joseortega_p2_ap2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Entidades")
data class EntidadEntity(
    @PrimaryKey
    val entidadId: Int,
    val dato1: String,
    val dato2: Int
)