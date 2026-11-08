package com.javiviz.sh01_pasteleriacrud.features.login.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javiviz.sh01_pasteleriacrud.core.ui.mensajeParaUsuario
import com.javiviz.sh01_pasteleriacrud.core.ui.validation.Validadores
import com.javiviz.sh01_pasteleriacrud.features.login.data.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Recibe el repositorio por constructor (lo arma LoginViewModelFactory).
 * Al iniciar sesión con éxito el repositorio guarda la sesión y la navegación
 * reacciona a ese cambio (ver AppNavHost), por eso aquí no hay evento de éxito.
 */
class LoginViewModel(
    private val repository: LoginRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(valor: String) {
        _uiState.update { it.copy(email = valor, emailError = null, errorGeneral = null) }
    }

    fun onPasswordChange(valor: String) {
        _uiState.update { it.copy(password = valor, passwordError = null, errorGeneral = null) }
    }

    fun onLoginClick() {
        val actual = _uiState.value
        if (actual.cargando) return

        val emailError = Validadores.email(actual.email)
        val passwordError = Validadores.passwordObligatoria(actual.password)
        if (emailError != null || passwordError != null) {
            _uiState.update { it.copy(emailError = emailError, passwordError = passwordError) }
            return
        }

        _uiState.update { it.copy(cargando = true, errorGeneral = null) }
        viewModelScope.launch {
            repository.login(actual.email, actual.password)
                .onSuccess {
                    _uiState.update { estado -> estado.copy(cargando = false, password = "") }
                }
                .onFailure { error ->
                    _uiState.update { estado ->
                        estado.copy(cargando = false, errorGeneral = error.mensajeParaUsuario())
                    }
                }
        }
    }
}
