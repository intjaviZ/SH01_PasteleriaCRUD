package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.repository

import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.CambioEstadoPedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.FiltroPedidos
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.NuevoPedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.PaginaPedidos
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.Pedido
import java.math.BigDecimal

interface PedidosRepository {
    suspend fun listar(filtro: FiltroPedidos): Result<PaginaPedidos>
    suspend fun crear(pedido: NuevoPedido): Result<Pedido>
    suspend fun obtenerPorId(id: Int): Result<Pedido>
    suspend fun pagar(id: Int, montoRecibido: BigDecimal?): Result<CambioEstadoPedido>
    suspend fun entregar(id: Int): Result<CambioEstadoPedido>
    suspend fun cancelar(id: Int, motivo: String?): Result<CambioEstadoPedido>
}
