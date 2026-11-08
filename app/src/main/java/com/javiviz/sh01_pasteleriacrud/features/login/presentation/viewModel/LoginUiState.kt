package com.javiviz.sh01_pasteleriacrud.features.login.presentation.viewModel

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val cargando: Boolean = false,
    val errorGeneral: String? = null
)
