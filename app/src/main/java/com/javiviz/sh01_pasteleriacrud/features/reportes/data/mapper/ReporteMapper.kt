package com.javiviz.sh01_pasteleriacrud.features.reportes.data.mapper

import com.javiviz.sh01_pasteleriacrud.core.model.DomainError
import com.javiviz.sh01_pasteleriacrud.core.network.mapper.toLocalDateOrThrow
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.dto.GrupoVentasDto
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.dto.ReporteVentasDto
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.dto.ResumenVentasDto
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.model.AgrupacionVentas
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.model.GrupoVentas
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.model.ReporteVentas
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.model.ResumenVentas

fun ReporteVentasDto.toReporteVentas(): ReporteVentas = ReporteVentas(
    fechaInicio = rango.fechaInicio.toLocalDateOrThrow(),
    fechaFin = rango.fechaFin.toLocalDateOrThrow(),
    agruparPor = AgrupacionVentas.entries.firstOrNull { it.valorApi == agruparPor }
        ?: throw DomainError.Desconocido("Agrupación no reconocida: $agruparPor"),
    resumen = resumen.toResumenVentas(),
    grupos = grupos.map { it.toGrupoVentas() }
)

private fun ResumenVentasDto.toResumenVentas(): ResumenVentas = ResumenVentas(
    totalVentas = totalVentas.toBigDecimal(),
    totalPedidos = totalPedidos,
    totalPorciones = totalPorciones,
    ticketPromedio = ticketPromedio.toBigDecimal(),
    pedidosCancelados = pedidosCancelados
)

private fun GrupoVentasDto.toGrupoVentas(): GrupoVentas = GrupoVentas(
    etiqueta = periodo ?: pastelNombre ?: "Sin etiqueta",
    totalVentas = totalVentas.toBigDecimal(),
    totalPedidos = totalPedidos,
    totalPorciones = totalPorciones
)
