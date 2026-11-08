package com.javiviz.sh01_pasteleriacrud.features.catalogo.di

import com.javiviz.sh01_pasteleriacrud.app.AppModule
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.datasource.CatalogoApiService
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.datasource.CatalogoRemoteDataSource
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.datasource.CatalogoRemoteDataSourceImpl
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.repository.CatalogoRepository
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.repository.CatalogoRepositoryImpl

class CatalogoModule(private val appModule: AppModule) {

    private fun createApiService(): CatalogoApiService =
        appModule.retrofit.create(CatalogoApiService::class.java)

    private fun createRemoteDataSource(): CatalogoRemoteDataSource =
        CatalogoRemoteDataSourceImpl(createApiService(), appModule.apiCaller)

    fun createRepository(): CatalogoRepository =
        CatalogoRepositoryImpl(createRemoteDataSource())

    val repository: CatalogoRepository by lazy { createRepository() }
}
