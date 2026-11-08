package com.javiviz.sh01_pasteleriacrud.features.reportes.data.datasource

import com.javiviz.sh01_pasteleriacrud.features.reportes.data.dto.ReporteVentasDto
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.model.AgrupacionVentas
import java.time.LocalDate

interface ReportesRemoteDataSource {
    suspend fun obtenerVentas(
        fechaInicio: LocalDate,
        fechaFin: LocalDate,
        agruparPor: AgrupacionVentas
    ): Result<ReporteVentasDto>
}
