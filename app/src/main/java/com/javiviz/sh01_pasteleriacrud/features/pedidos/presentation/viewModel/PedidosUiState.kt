package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.viewModel

import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.EstadoPedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.PedidoResumen

data class PedidosUiState(
    val pedidos: List<PedidoResumen> = emptyList(),
    val estadoFiltro: EstadoPedido? = null,
    /** Carga de la primera página (inicial, filtro nuevo o recarga). */
    val cargando: Boolean = true,
    val cargandoMas: Boolean = false,
    val paginaActual: Int = 0,
    val hayMas: Boolean = false,
    val totalRegistros: Int = 0,
    val error: String? = null
)
