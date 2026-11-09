package com.javiviz.sh01_pasteleriacrud.features.reportes.presentation.viewModel

import com.javiviz.sh01_pasteleriacrud.features.reportes.data.model.AgrupacionVentas
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.model.ReporteVentas
import java.time.LocalDate

/** Rangos de fecha de un toque. */
enum class AtajoRango(val etiqueta: String) {
    ULTIMOS_7_DIAS("7 días"),
    ULTIMOS_30_DIAS("30 días"),
    ESTE_MES("Este mes"),
    MES_ANTERIOR("Mes anterior");

    fun rango(hoy: LocalDate): Pair<LocalDate, LocalDate> = when (this) {
        ULTIMOS_7_DIAS -> hoy.minusDays(6) to hoy
        ULTIMOS_30_DIAS -> hoy.minusDays(29) to hoy
        ESTE_MES -> hoy.withDayOfMonth(1) to hoy
        MES_ANTERIOR -> {
            val primero = hoy.withDayOfMonth(1).minusMonths(1)
            primero to primero.withDayOfMonth(primero.lengthOfMonth())
        }
    }
}

data class ReportesUiState(
    val fechaInicio: LocalDate = LocalDate.now().withDayOfMonth(1),
    val fechaFin: LocalDate = LocalDate.now(),
    val agruparPor: AgrupacionVentas = AgrupacionVentas.SEMANA,
    /** Atajo activo; es null cuando el usuario escribe fechas a mano. */
    val atajo: AtajoRango? = AtajoRango.ESTE_MES,
    /** Error de validación local del rango (no llega a la red). */
    val errorRango: String? = null,
    val cargando: Boolean = true,
    val reporte: ReporteVentas? = null,
    /** Error de la consulta al servidor. */
    val error: String? = null
)
