package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model

data class PaginaPedidos(
    val data: List<PedidoResumen>,
    val page: Int,
    val limit: Int,
    val totalRegistros: Int,
    val totalPaginas: Int
)
