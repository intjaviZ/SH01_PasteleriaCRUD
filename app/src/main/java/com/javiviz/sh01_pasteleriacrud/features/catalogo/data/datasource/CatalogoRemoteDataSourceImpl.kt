package com.javiviz.sh01_pasteleriacrud.features.catalogo.data.datasource

import com.javiviz.sh01_pasteleriacrud.core.network.ApiCaller
import com.javiviz.sh01_pasteleriacrud.core.network.dto.DataListResponseDto
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.dto.ConfiguracionTamanosDto
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.dto.PastelDto
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.dto.RellenoDto

class CatalogoRemoteDataSourceImpl(
    private val api: CatalogoApiService,
    private val apiCaller: ApiCaller
) : CatalogoRemoteDataSource {

    override suspend fun obtenerPasteles(): Result<DataListResponseDto<PastelDto>> =
        apiCaller.execute { api.obtenerPasteles() }

    override suspend fun obtenerRellenos(): Result<DataListResponseDto<RellenoDto>> =
        apiCaller.execute { api.obtenerRellenos() }

    override suspend fun obtenerConfiguracionTamanos(): Result<ConfiguracionTamanosDto> =
        apiCaller.execute { api.obtenerConfiguracionTamanos() }
}
