package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.viewModel

import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.Pedido

enum class DialogoPedido { PAGAR, ENTREGAR, CANCELAR }

data class PedidoDetalleUiState(
    val cargando: Boolean = true,
    val pedido: Pedido? = null,
    /** Error de la carga inicial. */
    val error: String? = null,
    /** Hay una operación (pagar/entregar/cancelar) en curso. */
    val procesando: Boolean = false,
    val dialogo: DialogoPedido? = null,
    /** Error de la operación; se muestra dentro del diálogo abierto. */
    val errorAccion: String? = null,
    /** Mensaje de éxito de un solo uso (snackbar). */
    val mensaje: String? = null,
    /** Avisa a la lista de pedidos que debe refrescarse. */
    val pedidoModificado: Boolean = false
)
