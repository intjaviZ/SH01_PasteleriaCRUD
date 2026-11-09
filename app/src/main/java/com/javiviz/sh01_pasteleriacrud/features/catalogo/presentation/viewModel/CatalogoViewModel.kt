package com.javiviz.sh01_pasteleriacrud.features.catalogo.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javiviz.sh01_pasteleriacrud.core.ui.mensajeParaUsuario
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.repository.CatalogoRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatalogoViewModel(
    private val repository: CatalogoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CatalogoUiState())
    val uiState: StateFlow<CatalogoUiState> = _uiState.asStateFlow()

    init {
        cargar()
    }

    /** Las tres consultas son independientes, así que se lanzan en paralelo. */
    fun cargar() {
        _uiState.update { it.copy(cargando = true, error = null) }
        viewModelScope.launch {
            val pasteles = async { repository.obtenerPasteles() }
            val rellenos = async { repository.obtenerRellenos() }
            val config = async { repository.obtenerConfiguracionTamanos() }

            val resultadoPasteles = pasteles.await()
            val resultadoRellenos = rellenos.await()
            val resultadoConfig = config.await()

            val error = listOf(resultadoPasteles, resultadoRellenos, resultadoConfig)
                .firstNotNullOfOrNull { it.exceptionOrNull() }

            _uiState.update {
                it.copy(
                    cargando = false,
                    pasteles = resultadoPasteles.getOrDefault(emptyList()),
                    rellenos = resultadoRellenos.getOrDefault(emptyList()),
                    config = resultadoConfig.getOrNull(),
                    error = error?.mensajeParaUsuario()
                )
            }
        }
    }
}
