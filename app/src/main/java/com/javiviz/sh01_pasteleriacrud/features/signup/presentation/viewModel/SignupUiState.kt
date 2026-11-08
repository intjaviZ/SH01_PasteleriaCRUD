package com.javiviz.sh01_pasteleriacrud.features.signup.presentation.viewModel

data class SignupUiState(
    val nombre: String = "",
    val telefono: String = "",
    val email: String = "",
    val password: String = "",
    val confirmarPassword: String = "",
    val nombreError: String? = null,
    val telefonoError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmarError: String? = null,
    val cargando: Boolean = false,
    val errorGeneral: String? = null,
    /** La pantalla lo consume una vez para navegar al login y luego se reinicia. */
    val registroExitoso: Boolean = false
)
