package com.javiviz.sh01_pasteleriacrud.features.catalogo.data.repository

import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.ConfiguracionTamanos
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.PastelCatalogo
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.Relleno

interface CatalogoRepository {
    suspend fun obtenerPasteles(): Result<List<PastelCatalogo>>
    suspend fun obtenerRellenos(): Result<List<Relleno>>
    suspend fun obtenerConfiguracionTamanos(): Result<ConfiguracionTamanos>
}
