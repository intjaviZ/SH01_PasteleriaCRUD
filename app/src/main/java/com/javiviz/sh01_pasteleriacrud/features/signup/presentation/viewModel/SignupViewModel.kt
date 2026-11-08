package com.javiviz.sh01_pasteleriacrud.features.signup.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javiviz.sh01_pasteleriacrud.core.model.DomainError
import com.javiviz.sh01_pasteleriacrud.core.ui.mensajeParaUsuario
import com.javiviz.sh01_pasteleriacrud.core.ui.validation.Validadores
import com.javiviz.sh01_pasteleriacrud.features.signup.data.repository.SignupRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupViewModel(
    private val repository: SignupRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    fun onNombreChange(valor: String) {
        _uiState.update { it.copy(nombre = valor, nombreError = null, errorGeneral = null) }
    }

    fun onTelefonoChange(valor: String) {
        val soloDigitos = valor.filter { it.isDigit() }.take(10)
        _uiState.update { it.copy(telefono = soloDigitos, telefonoError = null, errorGeneral = null) }
    }

    fun onEmailChange(valor: String) {
        _uiState.update { it.copy(email = valor, emailError = null, errorGeneral = null) }
    }

    fun onPasswordChange(valor: String) {
        _uiState.update { it.copy(password = valor, passwordError = null, errorGeneral = null) }
    }

    fun onConfirmarPasswordChange(valor: String) {
        _uiState.update { it.copy(confirmarPassword = valor, confirmarError = null, errorGeneral = null) }
    }

    fun onRegistrarClick() {
        val actual = _uiState.value
        if (actual.cargando) return

        val nombreError = Validadores.nombre(actual.nombre)
        val telefonoError = Validadores.telefono(actual.telefono)
        val emailError = Validadores.email(actual.email)
        val passwordError = Validadores.passwordNueva(actual.password)
        val confirmarError = Validadores.confirmacion(actual.password, actual.confirmarPassword)

        if (listOf(nombreError, telefonoError, emailError, passwordError, confirmarError).any { it != null }) {
            _uiState.update {
                it.copy(
                    nombreError = nombreError,
                    telefonoError = telefonoError,
                    emailError = emailError,
                    passwordError = passwordError,
                    confirmarError = confirmarError
                )
            }
            return
        }

        _uiState.update { it.copy(cargando = true, errorGeneral = null) }
        viewModelScope.launch {
            repository.registrar(actual.nombre, actual.telefono, actual.email, actual.password)
                .onSuccess {
                    _uiState.update { estado -> estado.copy(cargando = false, registroExitoso = true) }
                }
                .onFailure { error ->
                    _uiState.update { estado -> conError(estado, error) }
                }
        }
    }

    fun consumirRegistroExitoso() {
        _uiState.update { it.copy(registroExitoso = false) }
    }

    /** Los errores que pertenecen a un campo se muestran en ese campo; el resto, arriba del botón. */
    private fun conError(estado: SignupUiState, error: Throwable): SignupUiState = when (error) {
        is DomainError.EmailDuplicado -> estado.copy(cargando = false, emailError = error.message)
        is DomainError.PasswordDebil -> estado.copy(cargando = false, passwordError = error.message)
        else -> estado.copy(cargando = false, errorGeneral = error.mensajeParaUsuario())
    }
}
