package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.datasource

import com.javiviz.sh01_pasteleriacrud.core.network.ApiCaller
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.CambioEstadoDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.CancelarRequestDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.NuevoPedidoRequestDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.PagarRequestDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.PedidoDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.PedidosPaginadosDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.FiltroPedidos

class PedidosRemoteDataSourceImpl(
    private val api: PedidosApiService,
    private val apiCaller: ApiCaller
) : PedidosRemoteDataSource {

    override suspend fun listar(filtro: FiltroPedidos): Result<PedidosPaginadosDto> =
        apiCaller.execute {
            api.listar(
                estado = filtro.estado?.name,
                fechaDesde = filtro.fechaDesde?.toString(),
                fechaHasta = filtro.fechaHasta?.toString(),
                campoFecha = filtro.campoFecha.valorApi,
                clienteId = filtro.clienteId,
                page = filtro.page,
                limit = filtro.limit
            )
        }

    override suspend fun crear(request: NuevoPedidoRequestDto): Result<PedidoDto> =
        apiCaller.execute { api.crear(request) }

    override suspend fun obtenerPorId(id: Int): Result<PedidoDto> =
        apiCaller.execute { api.obtenerPorId(id) }

    override suspend fun pagar(id: Int, request: PagarRequestDto): Result<CambioEstadoDto> =
        apiCaller.execute { api.pagar(id, request) }

    override suspend fun entregar(id: Int): Result<CambioEstadoDto> =
        apiCaller.execute { api.entregar(id) }

    override suspend fun cancelar(id: Int, request: CancelarRequestDto): Result<CambioEstadoDto> =
        apiCaller.execute { api.cancelar(id, request) }
}
