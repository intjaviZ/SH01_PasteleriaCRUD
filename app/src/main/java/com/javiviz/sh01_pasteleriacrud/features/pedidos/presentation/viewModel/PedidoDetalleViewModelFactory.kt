package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.repository.PedidosRepository

/** Factory con parámetro: cada pedido abierto necesita su propio ViewModel. */
class PedidoDetalleViewModelFactory(
    private val repository: PedidosRepository,
    private val pedidoId: Int
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PedidoDetalleViewModel::class.java)) {
            return PedidoDetalleViewModel(repository, pedidoId) as T
        }
        throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}
