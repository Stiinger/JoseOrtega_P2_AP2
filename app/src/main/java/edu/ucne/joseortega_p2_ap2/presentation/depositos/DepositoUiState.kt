package edu.ucne.joseortega_p2_ap2.presentation.depositos

import edu.ucne.joseortega_p2_ap2.data.local.entity.DepositoEntity

data class DepositoUiState(
    val depositoId: Int? = null,
    val fecha: String = "",
    val idCuenta: String = "",
    val concepto: String = "",
    val monto: String = "",
    val errorMessage: String? = null,
    val depositos: List<DepositoEntity> = emptyList(),
    val isLoading: Boolean = false,
)