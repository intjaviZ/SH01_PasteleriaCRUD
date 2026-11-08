package com.javiviz.sh01_pasteleriacrud.features.reportes.data.repository

import com.javiviz.sh01_pasteleriacrud.features.reportes.data.model.AgrupacionVentas
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.model.ReporteVentas
import java.time.LocalDate

interface ReportesRepository {
    suspend fun obtenerVentas(
        fechaInicio: LocalDate,
        fechaFin: LocalDate,
        agruparPor: AgrupacionVentas
    ): Result<ReporteVentas>
}
