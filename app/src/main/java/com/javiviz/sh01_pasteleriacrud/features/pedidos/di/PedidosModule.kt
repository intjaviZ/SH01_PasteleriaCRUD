package com.javiviz.sh01_pasteleriacrud.features.pedidos.di

import com.javiviz.sh01_pasteleriacrud.app.AppModule
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.datasource.PedidosApiService
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.datasource.PedidosRemoteDataSource
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.datasource.PedidosRemoteDataSourceImpl
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.repository.PedidosRepository
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.repository.PedidosRepositoryImpl
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.viewModel.NuevoPedidoViewModelFactory
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.viewModel.PedidoDetalleViewModelFactory
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.viewModel.PedidosViewModelFactory

class PedidosModule(private val appModule: AppModule) {

    private fun createApiService(): PedidosApiService =
        appModule.retrofit.create(PedidosApiService::class.java)

    private fun createRemoteDataSource(): PedidosRemoteDataSource =
        PedidosRemoteDataSourceImpl(createApiService(), appModule.apiCaller)

    fun createRepository(): PedidosRepository =
        PedidosRepositoryImpl(createRemoteDataSource())

    val repository: PedidosRepository by lazy { createRepository() }

    // ----- Factories de ViewModel -----

    fun createViewModelFactory(): PedidosViewModelFactory = PedidosViewModelFactory(repository)

    val viewModelFactory: PedidosViewModelFactory by lazy { createViewModelFactory() }

    /** Con argumento: no se guarda en un lazy porque cada pedido abierto tiene su propio id. */
    fun createDetalleViewModelFactory(pedidoId: Int): PedidoDetalleViewModelFactory =
        PedidoDetalleViewModelFactory(repository, pedidoId)

    /** El formulario de pedido necesita también el catálogo, por eso toma su repositorio. */
    fun createNuevoPedidoViewModelFactory(): NuevoPedidoViewModelFactory =
        NuevoPedidoViewModelFactory(appModule.catalogoModule.repository, repository)

    val nuevoPedidoViewModelFactory: NuevoPedidoViewModelFactory by lazy {
        createNuevoPedidoViewModelFactory()
    }
}
