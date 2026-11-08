package com.javiviz.sh01_pasteleriacrud.features.catalogo.data.datasource

import com.javiviz.sh01_pasteleriacrud.core.network.dto.DataListResponseDto
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.dto.ConfiguracionTamanosDto
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.dto.PastelDto
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.dto.RellenoDto

interface CatalogoRemoteDataSource {
    suspend fun obtenerPasteles(): Result<DataListResponseDto<PastelDto>>
    suspend fun obtenerRellenos(): Result<DataListResponseDto<RellenoDto>>
    suspend fun obtenerConfiguracionTamanos(): Result<ConfiguracionTamanosDto>
}
