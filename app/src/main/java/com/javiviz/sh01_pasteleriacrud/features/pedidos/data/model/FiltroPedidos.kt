package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model

import java.time.LocalDate

enum class CampoFechaPedido(val valorApi: String) {
    CREACION("creacion"),
    ENTREGA("entrega")
}

data class FiltroPedidos(
    val estado: EstadoPedido? = null,
    val fechaDesde: LocalDate? = null,
    val fechaHasta: LocalDate? = null,
    val campoFecha: CampoFechaPedido = CampoFechaPedido.CREACION,
    /** Solo lo respeta la API para REPOSTERA. */
    val clienteId: Int? = null,
    val page: Int = 1,
    val limit: Int = 20
)
