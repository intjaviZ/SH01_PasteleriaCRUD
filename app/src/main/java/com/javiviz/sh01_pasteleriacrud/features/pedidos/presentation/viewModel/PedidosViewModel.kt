package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javiviz.sh01_pasteleriacrud.core.ui.mensajeParaUsuario
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.EstadoPedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.FiltroPedidos
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.repository.PedidosRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Sirve a los dos roles: la API ya devuelve solo los pedidos del cliente
 * o todos los de la repostera según el token, así que aquí no hay lógica por rol.
 */
class PedidosViewModel(
    private val repository: PedidosRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PedidosUiState())
    val uiState: StateFlow<PedidosUiState> = _uiState.asStateFlow()

    private var cargaJob: Job? = null

    init {
        cargarPagina(1)
    }

    fun onFiltroEstadoChange(estado: EstadoPedido?) {
        if (estado == _uiState.value.estadoFiltro) return
        _uiState.update { it.copy(estadoFiltro = estado, pedidos = emptyList(), hayMas = false) }
        cargarPagina(1)
    }

    fun recargar() = cargarPagina(1)

    fun cargarMas() {
        val actual = _uiState.value
        if (actual.cargando || actual.cargandoMas || !actual.hayMas) return
        cargarPagina(actual.paginaActual + 1)
    }

    private fun cargarPagina(pagina: Int) {
        cargaJob?.cancel()
        val esPrimera = pagina == 1

        _uiState.update {
            if (esPrimera) it.copy(cargando = true, cargandoMas = false, error = null)
            else it.copy(cargandoMas = true, error = null)
        }

        cargaJob = viewModelScope.launch {
            val filtro = FiltroPedidos(
                estado = _uiState.value.estadoFiltro,
                page = pagina,
                limit = TAMANO_PAGINA
            )
            repository.listar(filtro).fold(
                onSuccess = { resultado ->
                    _uiState.update { s ->
                        s.copy(
                            cargando = false,
                            cargandoMas = false,
                            pedidos = if (esPrimera) resultado.data else s.pedidos + resultado.data,
                            paginaActual = resultado.page,
                            hayMas = resultado.page < resultado.totalPaginas,
                            totalRegistros = resultado.totalRegistros
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update { s ->
                        s.copy(cargando = false, cargandoMas = false, error = error.mensajeParaUsuario())
                    }
                }
            )
        }
    }

    private companion object {
        const val TAMANO_PAGINA = 20
    }
}
