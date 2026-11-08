package com.javiviz.sh01_pasteleriacrud.features.reportes.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReporteVentasDto(
    val rango: RangoDto,
    @SerialName("agrupar_por") val agruparPor: String,
    val resumen: ResumenVentasDto,
    val grupos: List<GrupoVentasDto> = emptyList()
)

@Serializable
data class RangoDto(
    @SerialName("fecha_inicio") val fechaInicio: String,
    @SerialName("fecha_fin") val fechaFin: String
)

@Serializable
data class ResumenVentasDto(
    @SerialName("total_ventas") val totalVentas: Double,
    @SerialName("total_pedidos") val totalPedidos: Int,
    @SerialName("total_porciones") val totalPorciones: Int,
    @SerialName("ticket_promedio") val ticketPromedio: Double,
    @SerialName("pedidos_cancelados") val pedidosCancelados: Int
)

/** Con agrupar_por = pastel llegan pastel_*; en los demás casos llega periodo. */
@Serializable
data class GrupoVentasDto(
    val periodo: String? = null,
    val desde: String? = null,
    val hasta: String? = null,
    @SerialName("pastel_catalogo_id") val pastelCatalogoId: Int? = null,
    @SerialName("pastel_nombre") val pastelNombre: String? = null,
    @SerialName("total_ventas") val totalVentas: Double,
    @SerialName("total_pedidos") val totalPedidos: Int,
    @SerialName("total_porciones") val totalPorciones: Int
)
