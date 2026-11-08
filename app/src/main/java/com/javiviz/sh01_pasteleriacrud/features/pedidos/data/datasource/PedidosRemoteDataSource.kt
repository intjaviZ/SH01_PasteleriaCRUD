package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.datasource

import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.CambioEstadoDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.CancelarRequestDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.NuevoPedidoRequestDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.PagarRequestDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.PedidoDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.PedidosPaginadosDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.FiltroPedidos

interface PedidosRemoteDataSource {
    suspend fun listar(filtro: FiltroPedidos): Result<PedidosPaginadosDto>
    suspend fun crear(request: NuevoPedidoRequestDto): Result<PedidoDto>
    suspend fun obtenerPorId(id: Int): Result<PedidoDto>
    suspend fun pagar(id: Int, request: PagarRequestDto): Result<CambioEstadoDto>
    suspend fun entregar(id: Int): Result<CambioEstadoDto>
    suspend fun cancelar(id: Int, request: CancelarRequestDto): Result<CambioEstadoDto>
}
