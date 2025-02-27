package edu.ucne.joseortega_p2_ap2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object DepositoList : Screen()
    @Serializable
    data class Deposito(val depositoId: Int) : Screen()
}