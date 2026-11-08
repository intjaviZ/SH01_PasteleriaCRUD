package com.javiviz.sh01_pasteleriacrud.features.login.presentation.screens

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javiviz.sh01_pasteleriacrud.PasteleriaApplication
import com.javiviz.sh01_pasteleriacrud.core.ui.components.BannerMensaje
import com.javiviz.sh01_pasteleriacrud.core.ui.components.EncabezadoAuth
import com.javiviz.sh01_pasteleriacrud.features.login.presentation.components.LoginFormulario
import com.javiviz.sh01_pasteleriacrud.features.login.presentation.viewModel.LoginViewModel

@Composable
fun LoginScreen(
    registroExitoso: Boolean,
    onIrASignup: () -> Unit,
    viewModel: LoginViewModel = viewModel(
        factory = (LocalContext.current.applicationContext as PasteleriaApplication)
            .appModule.loginModule.viewModelFactory
    )
) {
    val uiState by viewModel.uiState.collectAsState()

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
                titulo = "Pastelería",
                subtitulo = "Inicia sesión para continuar"
            )
            Spacer(Modifier.height(24.dp))

            if (registroExitoso) {
                BannerMensaje(texto = "Cuenta creada. Ahora inicia sesión.", esError = false)
                Spacer(Modifier.height(12.dp))
            }

            LoginFormulario(
                uiState = uiState,
                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange,
                onSubmit = viewModel::onLoginClick
            )

            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onIrASignup, enabled = !uiState.cargando) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }
    }
}
