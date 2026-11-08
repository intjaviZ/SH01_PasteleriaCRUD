package com.javiviz.sh01_pasteleriacrud.features.signup.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javiviz.sh01_pasteleriacrud.PasteleriaApplication
import com.javiviz.sh01_pasteleriacrud.core.ui.components.EncabezadoAuth
import com.javiviz.sh01_pasteleriacrud.features.signup.presentation.components.SignupFormulario
import com.javiviz.sh01_pasteleriacrud.features.signup.presentation.viewModel.SignupViewModel

@Composable
fun SignupScreen(
    onRegistroExitoso: () -> Unit,
    onIrALogin: () -> Unit,
    viewModel: SignupViewModel = viewModel(
        factory = (LocalContext.current.applicationContext as PasteleriaApplication)
            .appModule.signupModule.viewModelFactory
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.registroExitoso) {
        if (uiState.registroExitoso) {
            onRegistroExitoso()
            viewModel.consumirRegistroExitoso()
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EncabezadoAuth(
                titulo = "Crear cuenta",
                subtitulo = "Regístrate para hacer tus pedidos"
            )
            Spacer(Modifier.height(24.dp))

            SignupFormulario(
                uiState = uiState,
                onNombreChange = viewModel::onNombreChange,
                onTelefonoChange = viewModel::onTelefonoChange,
                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange,
                onConfirmarPasswordChange = viewModel::onConfirmarPasswordChange,
                onSubmit = viewModel::onRegistrarClick
            )

            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onIrALogin, enabled = !uiState.cargando) {
                Text("¿Ya tienes cuenta? Inicia sesión")
            }
        }
    }
}
