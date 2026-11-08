package com.javiviz.sh01_pasteleriacrud.features.reportes.data.repository

import com.javiviz.sh01_pasteleriacrud.features.reportes.data.datasource.ReportesRemoteDataSource
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.mapper.toReporteVentas
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.model.AgrupacionVentas
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.model.ReporteVentas
import java.time.LocalDate

class ReportesRepositoryImpl(
    private val remoteDataSource: ReportesRemoteDataSource
) : ReportesRepository {

    override suspend fun obtenerVentas(
        fechaInicio: LocalDate,
        fechaFin: LocalDate,
        agruparPor: AgrupacionVentas
    ): Result<ReporteVentas> =
        remoteDataSource.obtenerVentas(fechaInicio, fechaFin, agruparPor)
            .mapCatching { it.toReporteVentas() }
}
