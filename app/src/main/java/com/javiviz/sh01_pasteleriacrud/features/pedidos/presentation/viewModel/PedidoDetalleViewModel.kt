package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javiviz.sh01_pasteleriacrud.core.model.DomainError
import com.javiviz.sh01_pasteleriacrud.core.ui.mensajeParaUsuario
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.CambioEstadoPedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.repository.PedidosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Detalle de un pedido y sus operaciones de estado. Los dos roles usan este mismo ViewModel;
 * qué botones se muestran lo decide la pantalla con EstadoPedido.puedeCancelarPor(rol), etc.
 */
class PedidoDetalleViewModel(
    private val repository: PedidosRepository,
    private val pedidoId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(PedidoDetalleUiState())
    val uiState: StateFlow<PedidoDetalleUiState> = _uiState.asStateFlow()

    init {
        cargar()
    }

    fun cargar() {
        _uiState.update { it.copy(cargando = true, error = null) }
        viewModelScope.launch {
            repository.obtenerPorId(pedidoId).fold(
                onSuccess = { pedido ->
                    _uiState.update { it.copy(cargando = false, pedido = pedido) }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(cargando = false, error = error.mensajeParaUsuario()) }
                }
            )
        }
    }

    fun abrirDialogo(dialogo: DialogoPedido) {
        _uiState.update { it.copy(dialogo = dialogo, errorAccion = null) }
    }

    fun cerrarDialogo() {
        if (_uiState.value.procesando) return
        _uiState.update { it.copy(dialogo = null, errorAccion = null) }
    }

    fun confirmarPago(montoTexto: String) {
        val monto = montoTexto.trim().replace(',', '.').toBigDecimalOrNull()
        if (montoTexto.isNotBlank() && monto == null) {
            _uiState.update { it.copy(errorAccion = "Ingresa un monto válido.") }
            return
        }
        ejecutar("Pago registrado.") { repository.pagar(pedidoId, monto) }
    }

    fun confirmarEntrega() {
        ejecutar("Pedido marcado como entregado.") { repository.entregar(pedidoId) }
    }

    fun confirmarCancelacion(motivo: String) {
        ejecutar("Pedido cancelado.") { repository.cancelar(pedidoId, motivo) }
    }

    fun consumirMensaje() {
        _uiState.update { it.copy(mensaje = null) }
    }

    fun consumirModificado() {
        _uiState.update { it.copy(pedidoModificado = false) }
    }

    private fun ejecutar(
        mensajeExito: String,
        operacion: suspend () -> Result<CambioEstadoPedido>
    ) {
        if (_uiState.value.procesando) return
        _uiState.update { it.copy(procesando = true, errorAccion = null) }

        viewModelScope.launch {
            operacion().fold(
                onSuccess = { cambio ->
                    // Se vuelve a pedir el detalle completo; si falla, al menos se actualiza el estado.
                    val actualizado = repository.obtenerPorId(pedidoId).getOrNull()
                    _uiState.update { s ->
                        s.copy(
                            procesando = false,
                            dialogo = null,
                            pedido = actualizado ?: s.pedido?.copy(estado = cambio.estado),
                            mensaje = mensajeExito,
                            pedidoModificado = true
                        )
                    }
                },
                onFailure = { error ->
                    // Si el estado cambió desde otro dispositivo, se refresca para mostrar el real.
                    val actualizado = if (error is DomainError.TransicionInvalida) {
                        repository.obtenerPorId(pedidoId).getOrNull()
                    } else null
                    _uiState.update { s ->
                        s.copy(
                            procesando = false,
                            pedido = actualizado ?: s.pedido,
                            errorAccion = error.mensajeParaUsuario(),
                            pedidoModificado = s.pedidoModificado || actualizado != null
                        )
                    }
                }
            )
        }
    }
}
