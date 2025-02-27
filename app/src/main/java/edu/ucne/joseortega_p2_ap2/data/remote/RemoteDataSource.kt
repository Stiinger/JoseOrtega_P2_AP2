package edu.ucne.joseortega_p2_ap2.data.remote

import edu.ucne.joseortega_p2_ap2.data.remote.dto.DepositoDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val ticketManagerApi: DepositoApi
) {
    suspend fun getDepositos() = ticketManagerApi.getDepositos()
    suspend fun getDeposito(id: Int) = ticketManagerApi.getDeposito(id)
    suspend fun saveDeposito(depositoDto: DepositoDto) = ticketManagerApi.saveDeposito(depositoDto)
    suspend fun updateDeposito(id: Int, depositoDto: DepositoDto) = ticketManagerApi.updateDeposito(id, depositoDto)
    suspend fun deleteDeposito(id: Int) = ticketManagerApi.deleteDeposito(id)
}