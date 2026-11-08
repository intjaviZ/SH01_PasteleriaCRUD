package com.javiviz.sh01_pasteleriacrud.features.catalogo.data.repository

import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.datasource.CatalogoRemoteDataSource
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.mapper.toConfiguracionTamanos
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.mapper.toPastelCatalogo
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.mapper.toRelleno
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.ConfiguracionTamanos
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.PastelCatalogo
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.Relleno

class CatalogoRepositoryImpl(
    private val remoteDataSource: CatalogoRemoteDataSource
) : CatalogoRepository {

    override suspend fun obtenerPasteles(): Result<List<PastelCatalogo>> =
        remoteDataSource.obtenerPasteles()
            .mapCatching { respuesta -> respuesta.data.map { it.toPastelCatalogo() } }

    override suspend fun obtenerRellenos(): Result<List<Relleno>> =
        remoteDataSource.obtenerRellenos()
            .mapCatching { respuesta -> respuesta.data.map { it.toRelleno() } }

    override suspend fun obtenerConfiguracionTamanos(): Result<ConfiguracionTamanos> =
        remoteDataSource.obtenerConfiguracionTamanos()
            .mapCatching { it.toConfiguracionTamanos() }
}
