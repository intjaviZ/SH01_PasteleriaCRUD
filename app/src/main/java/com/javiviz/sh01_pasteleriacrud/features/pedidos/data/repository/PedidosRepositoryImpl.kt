package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.repository

import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.datasource.PedidosRemoteDataSource
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.CancelarRequestDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.PagarRequestDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.mapper.toCambioEstadoPedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.mapper.toPaginaPedidos
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.mapper.toPedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.mapper.toRequestDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.CambioEstadoPedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.FiltroPedidos
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.NuevoPedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.PaginaPedidos
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.Pedido
import java.math.BigDecimal

class PedidosRepositoryImpl(
    private val remoteDataSource: PedidosRemoteDataSource
) : PedidosRepository {

    override suspend fun listar(filtro: FiltroPedidos): Result<PaginaPedidos> =
        remoteDataSource.listar(filtro).mapCatching { it.toPaginaPedidos() }

    override suspend fun crear(pedido: NuevoPedido): Result<Pedido> =
        remoteDataSource.crear(pedido.toRequestDto()).mapCatching { it.toPedido() }

    override suspend fun obtenerPorId(id: Int): Result<Pedido> =
        remoteDataSource.obtenerPorId(id).mapCatching { it.toPedido() }

    override suspend fun pagar(id: Int, montoRecibido: BigDecimal?): Result<CambioEstadoPedido> =
        remoteDataSource.pagar(id, PagarRequestDto(montoRecibido?.toDouble()))
            .mapCatching { it.toCambioEstadoPedido() }

    override suspend fun entregar(id: Int): Result<CambioEstadoPedido> =
        remoteDataSource.entregar(id).mapCatching { it.toCambioEstadoPedido() }

    override suspend fun cancelar(id: Int, motivo: String?): Result<CambioEstadoPedido> =
        remoteDataSource.cancelar(id, CancelarRequestDto(motivo?.trim()?.ifEmpty { null }))
            .mapCatching { it.toCambioEstadoPedido() }
}
