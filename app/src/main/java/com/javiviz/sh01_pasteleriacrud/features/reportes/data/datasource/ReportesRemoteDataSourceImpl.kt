package com.javiviz.sh01_pasteleriacrud.features.reportes.data.datasource

import com.javiviz.sh01_pasteleriacrud.core.network.ApiCaller
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.dto.ReporteVentasDto
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.model.AgrupacionVentas
import java.time.LocalDate

class ReportesRemoteDataSourceImpl(
    private val api: ReportesApiService,
    private val apiCaller: ApiCaller
) : ReportesRemoteDataSource {

    override suspend fun obtenerVentas(
        fechaInicio: LocalDate,
        fechaFin: LocalDate,
        agruparPor: AgrupacionVentas
    ): Result<ReporteVentasDto> =
        apiCaller.execute {
            api.obtenerVentas(
                fechaInicio = fechaInicio.toString(),
                fechaFin = fechaFin.toString(),
                agruparPor = agruparPor.valorApi
            )
        }
}
