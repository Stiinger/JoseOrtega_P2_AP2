package edu.ucne.joseortega_p2_ap2.presentation.depositos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.joseortega_p2_ap2.data.local.entity.DepositoEntity
import edu.ucne.joseortega_p2_ap2.data.remote.Resource
import edu.ucne.joseortega_p2_ap2.data.remote.dto.DepositoDto
import edu.ucne.joseortega_p2_ap2.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class DepositoViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DepositoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getDepositos()
    }

    fun save() {
        viewModelScope.launch {
            if (isValid()) {
                repository.save(_uiState.value.toEntity())
            }
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }

    fun update() {
        viewModelScope.launch {
            _uiState.value.depositoId.let {
                if (it != null) {
                    repository.update(
                        it, DepositoDto(
                            idDeposito = _uiState.value.depositoId,
                            fecha = _uiState.value.fecha,
                            concepto = _uiState.value.concepto,
                            monto = _uiState.value.monto.toDouble(),
                            idCuenta = _uiState.value.idCuenta.toInt()
                        )
                    )
                }
            }
        }
    }

    fun new() {
        _uiState.value = DepositoUiState()
    }

    fun onConceptoChange(concepto: String) {
        _uiState.update {
            it.copy(
                concepto = concepto,
                errorMessage = if (concepto.isBlank()) "Debes rellenar el campo Concepto"
                else null
            )
        }
    }

    fun onMontoChange(monto: String) {
        _uiState.update {
            val montoDouble = monto.toDouble()
            it.copy(
                monto = monto,
                errorMessage = when {
                    montoDouble <= 0 -> "Debe ingresar un valor mayor a 0"
                    else -> null
                }
            )
        }
    }

    fun onCuentaChange(cuenta: String) {
        _uiState.update {
            val cuentaInt = cuenta.toInt()
            it.copy(
                idCuenta = cuenta,
                errorMessage = when {
                    cuentaInt <= 0 -> "Debe ingresar un valor mayor a 0"
                    else -> null
                }
            )
        }
    }

    fun onFechaChange(fecha: String) {
        val formatoEntrada = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formatoSalida = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date = formatoEntrada.parse(fecha)
        val formattedDate = formatoSalida.format(date!!)

        _uiState.update {
            it.copy(
                fecha = formattedDate,
                errorMessage = if (fecha.isBlank()) "Debes rellenar el campo Fecha"
                else null
            )
        }
    }

    fun find(depositoId: Int) {
        viewModelScope.launch {
            val deposito = repository.find(depositoId)

            if (deposito != null) {
                _uiState.update {
                    it.copy(
                        depositoId = deposito.depositoId,
                        fecha = deposito.fecha,
                        concepto = deposito.concepto,
                        monto = deposito.monto.toString(),
                        idCuenta = deposito.idCuenta.toString()
                    )
                }
            }
        }
    }

    fun getDepositos() {
        viewModelScope.launch {
            repository.getEntidades().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                depositos = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message ?: "Error desconocido",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun isValid(): Boolean {
        return uiState.value.concepto.isNotBlank()
                && uiState.value.fecha.isNotBlank()
                && uiState.value.monto.isNotBlank()
                && uiState.value.idCuenta.isNotBlank()
    }

}

fun DepositoUiState.toEntity() = DepositoDto(
    idDeposito = depositoId,
    fecha = fecha,
    concepto = concepto,
    monto = monto.toDouble(),
    idCuenta = idCuenta.toInt()
)