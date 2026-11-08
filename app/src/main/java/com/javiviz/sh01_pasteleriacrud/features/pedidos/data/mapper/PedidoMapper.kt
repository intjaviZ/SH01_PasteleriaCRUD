package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.mapper

import com.javiviz.sh01_pasteleriacrud.core.model.Rol
import com.javiviz.sh01_pasteleriacrud.core.network.mapper.toEnumOrThrow
import com.javiviz.sh01_pasteleriacrud.core.network.mapper.toInstantOrThrow
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.CambioEstadoDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.ClienteResumenDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.DistribucionDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.ItemPedidoDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.NuevoItemRequestDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.NuevoPedidoRequestDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.PedidoDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.PedidoResumenDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto.PedidosPaginadosDto
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.CambioEstadoPedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.ClienteResumen
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.DistribucionTorta
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.ItemPedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.NuevoItemPedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.NuevoPedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.PaginaPedidos
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.Pedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.PedidoResumen

// ---------- Response -> modelo ----------

fun PedidoDto.toPedido(): Pedido = Pedido(
    id = id,
    clienteId = clienteId,
    cliente = cliente?.toClienteResumen(),
    fechaCreacion = fechaCreacion.toInstantOrThrow(),
    fechaEntregaEsperada = fechaEntregaEsperada.toInstantOrThrow(),
    estado = estado.toEnumOrThrow("estado"),
    totalEstimado = totalEstimado.toBigDecimal(),
    metodoPago = metodoPago.toEnumOrThrow("metodo_pago"),
    notasEntrega = notasEntrega,
    items = items.map { it.toItemPedido() }
)

private fun ClienteResumenDto.toClienteResumen(): ClienteResumen =
    ClienteResumen(id = id, nombre = nombre, telefono = telefono)

private fun ItemPedidoDto.toItemPedido(): ItemPedido = ItemPedido(
    id = id,
    pastelId = pastelCatalogoId,
    pastelNombre = pastelNombre,
    rellenoId = rellenoId,
    rellenoNombre = rellenoNombre,
    porcionesTotales = porcionesTotales,
    colorPrimario = colorPrimario,
    colorSecundario = colorSecundario,
    dedicatoria = dedicatoria,
    precioUnitario = precioUnitario.toBigDecimal(),
    distribucion = distribucion.map { DistribucionTorta(it.porcionesTorta, it.cantidad) }
)

fun PedidoResumenDto.toPedidoResumen(): PedidoResumen = PedidoResumen(
    id = id,
    clienteId = clienteId,
    fechaCreacion = fechaCreacion.toInstantOrThrow(),
    fechaEntregaEsperada = fechaEntregaEsperada.toInstantOrThrow(),
    estado = estado.toEnumOrThrow("estado"),
    totalEstimado = totalEstimado.toBigDecimal(),
    metodoPago = metodoPago.toEnumOrThrow("metodo_pago"),
    notasEntrega = notasEntrega,
    totalItems = totalItems
)

fun PedidosPaginadosDto.toPaginaPedidos(): PaginaPedidos = PaginaPedidos(
    data = data.map { it.toPedidoResumen() },
    page = paginacion.page,
    limit = paginacion.limit,
    totalRegistros = paginacion.totalRegistros,
    totalPaginas = paginacion.totalPaginas
)

fun CambioEstadoDto.toCambioEstadoPedido(): CambioEstadoPedido = CambioEstadoPedido(
    id = id,
    estado = estado.toEnumOrThrow("estado"),
    fechaCambio = (fechaPago ?: fechaEntregaReal ?: fechaCancelacion)?.toInstantOrThrow(),
    montoRecibido = montoRecibido?.toBigDecimal(),
    motivoCancelacion = motivoCancelacion,
    canceladoPor = canceladoPor?.let { valor -> Rol.entries.firstOrNull { it.name == valor } }
)

// ---------- Modelo -> Request ----------

fun NuevoPedido.toRequestDto(): NuevoPedidoRequestDto = NuevoPedidoRequestDto(
    fechaEntregaEsperada = fechaEntregaEsperada.toString(),
    notasEntrega = notasEntrega,
    items = items.map { it.toRequestDto() }
)

private fun NuevoItemPedido.toRequestDto(): NuevoItemRequestDto = NuevoItemRequestDto(
    pastelCatalogoId = pastelId,
    rellenoId = rellenoId,
    porcionesTotales = porcionesTotales,
    colorPrimario = colorPrimario,
    colorSecundario = colorSecundario,
    dedicatoria = dedicatoria,
    distribucion = distribucion?.map { DistribucionDto(it.porcionesTorta, it.cantidad) }
)
