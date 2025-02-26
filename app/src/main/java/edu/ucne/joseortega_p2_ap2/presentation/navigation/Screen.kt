package edu.ucne.joseortega_p2_ap2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    data object EntidadList : Screen()
    @Serializable
    data class Entidad(val entidadId: Int) : Screen()
}