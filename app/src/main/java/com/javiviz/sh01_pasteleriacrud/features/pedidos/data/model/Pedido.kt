package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model

import java.math.BigDecimal
import java.time.Instant

/** Pedido completo (detalle / creación). El nombre del cliente solo viene en el detalle. */
data class Pedido(
    val id: Int,
    val clienteId: Int,
    val cliente: ClienteResumen?,
    val fechaCreacion: Instant,
    val fechaEntregaEsperada: Instant,
    val estado: EstadoPedido,
    val totalEstimado: BigDecimal,
    val metodoPago: MetodoPago,
    val notasEntrega: String?,
    val items: List<ItemPedido>
) {
    fun totalPorciones(): Int = items.sumOf { it.porcionesTotales }
}

data class ClienteResumen(
    val id: Int,
    val nombre: String,
    val telefono: String?
)
