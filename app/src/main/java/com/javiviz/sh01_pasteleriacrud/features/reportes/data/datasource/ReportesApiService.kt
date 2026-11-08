package com.javiviz.sh01_pasteleriacrud.features.reportes.data.datasource

import com.javiviz.sh01_pasteleriacrud.features.reportes.data.dto.ReporteVentasDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ReportesApiService {

    @GET("reportes/ventas")
    suspend fun obtenerVentas(
        @Query("fecha_inicio") fechaInicio: String,
        @Query("fecha_fin") fechaFin: String,
        @Query("agrupar_por") agruparPor: String
    ): ReporteVentasDto
}
