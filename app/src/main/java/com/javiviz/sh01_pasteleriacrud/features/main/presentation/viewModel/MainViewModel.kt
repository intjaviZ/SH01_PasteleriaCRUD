package com.javiviz.sh01_pasteleriacrud.features.main.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javiviz.sh01_pasteleriacrud.core.model.Sesion
import com.javiviz.sh01_pasteleriacrud.features.login.data.repository.LoginRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant

/** Fuente de verdad de "¿hay sesión?". La navegación raíz reacciona a este estado. */
class MainViewModel(
    private val repository: LoginRepository
) : ViewModel() {

    val uiState: StateFlow<MainUiState> = repository.observarSesion()
        .map<Sesion?, MainUiState> { sesion ->
            when {
                sesion == null -> MainUiState.SinSesion
                sesion.expiraEn.isBefore(Instant.now()) -> {
                    repository.cerrarSesion() // sesión vencida: se limpia el almacenamiento
                    MainUiState.SinSesion
                }
                else -> MainUiState.Autenticado(sesion.usuario)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), MainUiState.Cargando)

    fun cerrarSesion() {
        viewModelScope.launch { repository.cerrarSesion() }
    }
}
