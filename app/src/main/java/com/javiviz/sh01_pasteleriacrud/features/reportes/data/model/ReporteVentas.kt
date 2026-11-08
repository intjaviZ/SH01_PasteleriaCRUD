package com.javiviz.sh01_pasteleriacrud.features.reportes.data.model

import java.math.BigDecimal
import java.time.LocalDate

data class ReporteVentas(
    val fechaInicio: LocalDate,
    val fechaFin: LocalDate,
    val agruparPor: AgrupacionVentas,
    val resumen: ResumenVentas,
    val grupos: List<GrupoVentas>
)

data class ResumenVentas(
    val totalVentas: BigDecimal,
    val totalPedidos: Int,
    val totalPorciones: Int,
    val ticketPromedio: BigDecimal,
    val pedidosCancelados: Int
)

/** etiqueta = periodo (ej. 2026-W45) o nombre del pastel, según agruparPor. */
data class GrupoVentas(
    val etiqueta: String,
    val totalVentas: BigDecimal,
    val totalPedidos: Int,
    val totalPorciones: Int
)
