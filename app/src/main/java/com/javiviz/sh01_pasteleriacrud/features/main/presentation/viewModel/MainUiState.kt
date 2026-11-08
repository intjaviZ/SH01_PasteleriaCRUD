package com.javiviz.sh01_pasteleriacrud.features.main.presentation.viewModel

import com.javiviz.sh01_pasteleriacrud.core.model.Usuario

/** Cargando: aún se está leyendo DataStore. Evita mostrar el login un instante si ya hay sesión. */
sealed interface MainUiState {
    data object Cargando : MainUiState
    data object SinSesion : MainUiState
    data class Autenticado(val usuario: Usuario) : MainUiState
}
