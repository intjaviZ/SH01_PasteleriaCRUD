package com.javiviz.sh01_pasteleriacrud.features.reportes.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javiviz.sh01_pasteleriacrud.core.ui.mensajeParaUsuario
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.model.AgrupacionVentas
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.repository.ReportesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * Estadísticas de ventas (solo REPOSTERA; la ruta está protegida en MainNavHost).
 * Cualquier cambio de filtro vuelve a consultar; si el rango no es válido ni se llama a la API.
 */
class ReportesViewModel(
    private val repository: ReportesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportesUiState())
    val uiState: StateFlow<ReportesUiState> = _uiState.asStateFlow()

    private var cargaJob: Job? = null

    init {
        cargar()
    }

    fun onAtajoSeleccionado(atajo: AtajoRango) {
        val (inicio, fin) = atajo.rango(LocalDate.now())
        _uiState.update { it.copy(fechaInicio = inicio, fechaFin = fin, atajo = atajo) }
        cargar()
    }

    fun onFechaInicioChange(fecha: LocalDate) {
        _uiState.update { it.copy(fechaInicio = fecha, atajo = null) }
        cargar()
    }

    fun onFechaFinChange(fecha: LocalDate) {
        _uiState.update { it.copy(fechaFin = fecha, atajo = null) }
        cargar()
    }

    fun onAgrupacionChange(agrupacion: AgrupacionVentas) {
        if (agrupacion == _uiState.value.agruparPor) return
        _uiState.update { it.copy(agruparPor = agrupacion) }
        cargar()
    }

    fun cargar() {
        cargaJob?.cancel()
        val filtros = _uiState.value

        val errorRango = validarRango(filtros.fechaInicio, filtros.fechaFin)
        if (errorRango != null) {
            _uiState.update { it.copy(errorRango = errorRango, cargando = false, error = null, reporte = null) }
            return
        }

        _uiState.update { it.copy(errorRango = null, cargando = true, error = null) }
        cargaJob = viewModelScope.launch {
            repository.obtenerVentas(filtros.fechaInicio, filtros.fechaFin, filtros.agruparPor).fold(
                onSuccess = { reporte ->
                    _uiState.update { it.copy(cargando = false, reporte = reporte) }
                },
                onFailure = { error ->
                    // Se descarta el reporte anterior: ya no corresponde a los filtros elegidos.
                    _uiState.update {
                        it.copy(cargando = false, reporte = null, error = error.mensajeParaUsuario())
                    }
                }
            )
        }
    }

    /** Mismas reglas que la API: inicio <= fin y máximo 366 días (inclusive). */
    private fun validarRango(inicio: LocalDate, fin: LocalDate): String? = when {
        inicio.isAfter(fin) -> "La fecha inicial no puede ser posterior a la final."
        ChronoUnit.DAYS.between(inicio, fin) + 1 > MAX_DIAS -> "El rango máximo es de $MAX_DIAS días."
        else -> null
    }

    private companion object {
        const val MAX_DIAS = 366
    }
}
