package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.repository.CatalogoRepository
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.repository.PedidosRepository

class NuevoPedidoViewModelFactory(
    private val catalogoRepository: CatalogoRepository,
    private val pedidosRepository: PedidosRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NuevoPedidoViewModel::class.java)) {
            return NuevoPedidoViewModel(catalogoRepository, pedidosRepository) as T
        }
        throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}
