package com.javiviz.sh01_pasteleriacrud.features.login.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.javiviz.sh01_pasteleriacrud.core.ui.components.BannerMensaje
import com.javiviz.sh01_pasteleriacrud.core.ui.components.BotonPrincipal
import com.javiviz.sh01_pasteleriacrud.core.ui.components.CampoPassword
import com.javiviz.sh01_pasteleriacrud.core.ui.components.CampoTexto
import com.javiviz.sh01_pasteleriacrud.features.login.presentation.viewModel.LoginUiState

/** Formulario sin estado propio: todo llega por parámetros y se reporta por callbacks. */
@Composable
fun LoginFormulario(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        CampoTexto(
            valor = uiState.email,
            onValorChange = onEmailChange,
            etiqueta = "Email",
            error = uiState.emailError,
            enabled = !uiState.cargando,
            leadingIcon = Icons.Default.Email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )

        CampoPassword(
            valor = uiState.password,
            onValorChange = onPasswordChange,
            etiqueta = "Contraseña",
            error = uiState.passwordError,
            enabled = !uiState.cargando,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                onSubmit()
            })
        )

        uiState.errorGeneral?.let { BannerMensaje(texto = it, esError = true) }

        BotonPrincipal(
            texto = "Iniciar sesión",
            onClick = onSubmit,
            cargando = uiState.cargando
        )
    }
}
