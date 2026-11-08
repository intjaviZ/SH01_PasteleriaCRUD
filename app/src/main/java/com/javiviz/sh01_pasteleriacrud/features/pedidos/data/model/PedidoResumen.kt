package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model

import java.math.BigDecimal
import java.time.Instant

/** Fila de GET /pedidos: pedido sin items, con el conteo de items. */
data class PedidoResumen(
    val id: Int,
    val clienteId: Int,
    val fechaCreacion: Instant,
    val fechaEntregaEsperada: Instant,
    val estado: EstadoPedido,
    val totalEstimado: BigDecimal,
    val metodoPago: MetodoPago,
    val notasEntrega: String?,
    val totalItems: Int
)
