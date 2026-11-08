package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PedidosPaginadosDto(
    val data: List<PedidoResumenDto>,
    val paginacion: PaginacionDto
)

@Serializable
data class PedidoResumenDto(
    val id: Int,
    @SerialName("cliente_id") val clienteId: Int,
    @SerialName("fecha_creacion") val fechaCreacion: String,
    @SerialName("fecha_entrega_esperada") val fechaEntregaEsperada: String,
    val estado: String,
    @SerialName("total_estimado") val totalEstimado: Double,
    @SerialName("metodo_pago") val metodoPago: String,
    @SerialName("notas_entrega") val notasEntrega: String? = null,
    @SerialName("total_items") val totalItems: Int
)

@Serializable
data class PaginacionDto(
    val page: Int,
    val limit: Int,
    @SerialName("total_registros") val totalRegistros: Int,
    @SerialName("total_paginas") val totalPaginas: Int
)
