package com.javiviz.sh01_pasteleriacrud.features.signup.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import com.javiviz.sh01_pasteleriacrud.features.signup.presentation.viewModel.SignupUiState

@Composable
fun SignupFormulario(
    uiState: SignupUiState,
    onNombreChange: (String) -> Unit,
    onTelefonoChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmarPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val siguiente = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        CampoTexto(
            valor = uiState.nombre,
            onValorChange = onNombreChange,
            etiqueta = "Nombre completo",
            error = uiState.nombreError,
            enabled = !uiState.cargando,
            leadingIcon = Icons.Default.Person,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = siguiente
        )

        CampoTexto(
            valor = uiState.telefono,
            onValorChange = onTelefonoChange,
            etiqueta = "Teléfono (10 dígitos)",
            error = uiState.telefonoError,
            enabled = !uiState.cargando,
            leadingIcon = Icons.Default.Phone,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
            keyboardActions = siguiente
        )

        CampoTexto(
            valor = uiState.email,
            onValorChange = onEmailChange,
            etiqueta = "Email",
            error = uiState.emailError,
            enabled = !uiState.cargando,
            leadingIcon = Icons.Default.Email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            keyboardActions = siguiente
        )

        CampoPassword(
            valor = uiState.password,
            onValorChange = onPasswordChange,
            etiqueta = "Contraseña (mínimo 8 caracteres)",
            error = uiState.passwordError,
            enabled = !uiState.cargando,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = siguiente
        )

        CampoPassword(
            valor = uiState.confirmarPassword,
            onValorChange = onConfirmarPasswordChange,
            etiqueta = "Confirmar contraseña",
            error = uiState.confirmarError,
            enabled = !uiState.cargando,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                onSubmit()
            })
        )

        uiState.errorGeneral?.let { BannerMensaje(texto = it, esError = true) }

        BotonPrincipal(
            texto = "Crear cuenta",
            onClick = onSubmit,
            cargando = uiState.cargando
        )
    }
}
