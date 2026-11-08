package com.javiviz.sh01_pasteleriacrud.features.pedidos.di

import com.javiviz.sh01_pasteleriacrud.app.AppModule
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.datasource.PedidosApiService
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.datasource.PedidosRemoteDataSource
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.datasource.PedidosRemoteDataSourceImpl
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.repository.PedidosRepository
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.repository.PedidosRepositoryImpl

class PedidosModule(private val appModule: AppModule) {

    private fun createApiService(): PedidosApiService =
        appModule.retrofit.create(PedidosApiService::class.java)

    private fun createRemoteDataSource(): PedidosRemoteDataSource =
        PedidosRemoteDataSourceImpl(createApiService(), appModule.apiCaller)

    fun createRepository(): PedidosRepository =
        PedidosRepositoryImpl(createRemoteDataSource())

    val repository: PedidosRepository by lazy { createRepository() }
}
