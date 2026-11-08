package com.javiviz.sh01_pasteleriacrud.features.catalogo.data.datasource

import com.javiviz.sh01_pasteleriacrud.core.network.dto.DataListResponseDto
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.dto.ConfiguracionTamanosDto
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.dto.PastelDto
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.dto.RellenoDto
import retrofit2.http.GET

interface CatalogoApiService {

    @GET("pasteles")
    suspend fun obtenerPasteles(): DataListResponseDto<PastelDto>

    @GET("rellenos")
    suspend fun obtenerRellenos(): DataListResponseDto<RellenoDto>

    @GET("configuracion-tamanos")
    suspend fun obtenerConfiguracionTamanos(): ConfiguracionTamanosDto
}
