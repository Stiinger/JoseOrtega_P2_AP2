package edu.ucne.joseortega_p2_ap2.data.repository

import edu.ucne.joseortega_p2_ap2.data.local.dao.Dao
import edu.ucne.joseortega_p2_ap2.data.local.entity.DepositoEntity
import edu.ucne.joseortega_p2_ap2.data.remote.RemoteDataSource
import edu.ucne.joseortega_p2_ap2.data.remote.Resource
import edu.ucne.joseortega_p2_ap2.data.remote.dto.DepositoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class Repository @Inject constructor(
    private val dao: Dao,
    private val remoteDataSource: RemoteDataSource,
) {
    fun getEntidades(): Flow<Resource<List<DepositoEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val depositoRemoto = remoteDataSource.getDepositos()

            val listaDepositos = depositoRemoto.map { dto ->
                DepositoEntity(
                    depositoId = dto.idDeposito,
                    fecha = dto.fecha,
                    idCuenta = dto.idCuenta,
                    concepto = dto.concepto,
                    monto = dto.monto
                )
            }
            dao.save(listaDepositos)
            emit(Resource.Success(listaDepositos))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
            emit(Resource.Error("Error de conexión $errorMessage"))
        } catch (e: Exception) {
            val depositosLocales = dao.getAll().first()
            if (depositosLocales.isNotEmpty())
                emit(Resource.Success(depositosLocales))
            else
                emit(Resource.Error("Error de conexión: ${e.message}"))
        }
    }

    suspend fun update(id: Int, depositoDto: DepositoDto) = remoteDataSource.updateDeposito(id, depositoDto)
    suspend fun find(id: Int): DepositoEntity? {
        val depositosDto = remoteDataSource.getDepositos()
        return depositosDto
            .firstOrNull { it.idDeposito == id }
            ?.let { depositoDto ->
                DepositoEntity(
                    depositoId = depositoDto.idDeposito,
                    fecha = depositoDto.fecha,
                    idCuenta = depositoDto.idCuenta,
                    concepto = depositoDto.concepto,
                    monto = depositoDto.monto
                )
            }
    }
    suspend fun save(depositoDto: DepositoDto) = remoteDataSource.saveDeposito(depositoDto)
    suspend fun delete(id: Int) = remoteDataSource.deleteDeposito(id)
}