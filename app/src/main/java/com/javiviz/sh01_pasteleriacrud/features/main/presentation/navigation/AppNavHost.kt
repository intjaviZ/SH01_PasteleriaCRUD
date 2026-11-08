package com.javiviz.sh01_pasteleriacrud.features.main.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.javiviz.sh01_pasteleriacrud.PasteleriaApplication
import com.javiviz.sh01_pasteleriacrud.features.login.presentation.screens.LoginScreen
import com.javiviz.sh01_pasteleriacrud.features.main.presentation.components.PantallaCarga
import com.javiviz.sh01_pasteleriacrud.features.main.presentation.screens.MainScreen
import com.javiviz.sh01_pasteleriacrud.features.main.presentation.viewModel.MainUiState
import com.javiviz.sh01_pasteleriacrud.features.main.presentation.viewModel.MainViewModel
import com.javiviz.sh01_pasteleriacrud.features.signup.presentation.screens.SignupScreen

/**
 * NavHost raíz. La sesión decide a dónde vas:
 *  - con sesión   -> MAIN (drawer + vistas)
 *  - sin sesión   -> LOGIN / SIGNUP
 * Al iniciar o cerrar sesión no se navega a mano: este NavHost reacciona al cambio de estado.
 */
@Composable
fun AppNavHost(
    viewModel: MainViewModel = viewModel(
        factory = (LocalContext.current.applicationContext as PasteleriaApplication)
            .appModule.mainModule.viewModelFactory
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState is MainUiState.Cargando) {
        PantallaCarga()
        return
    }

    val navController = rememberNavController()
    val destinoInicial = remember {
        if (uiState is MainUiState.Autenticado) Rutas.MAIN else Rutas.LOGIN_PATRON
    }

    LaunchedEffect(uiState) {
        val rutaActual = navController.currentBackStackEntry?.destination?.route
        when (uiState) {
            is MainUiState.Autenticado -> {
                if (rutaActual != Rutas.MAIN) {
                    navController.navigate(Rutas.MAIN) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            }
            MainUiState.SinSesion -> {
                if (rutaActual == Rutas.MAIN) {
                    navController.navigate(Rutas.login()) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            }
            MainUiState.Cargando -> Unit
        }
    }

    NavHost(navController = navController, startDestination = destinoInicial) {

        composable(
            route = Rutas.LOGIN_PATRON,
            arguments = listOf(
                navArgument(Rutas.ARG_REGISTRADO) {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { entry ->
            LoginScreen(
                registroExitoso = entry.arguments?.getBoolean(Rutas.ARG_REGISTRADO) ?: false,
                onIrASignup = { navController.navigate(Rutas.SIGNUP) }
            )
        }

        composable(Rutas.SIGNUP) {
            SignupScreen(
                onRegistroExitoso = {
                    navController.navigate(Rutas.login(registrado = true)) {
                        popUpTo(Rutas.LOGIN_PATRON) { inclusive = true }
                    }
                },
                onIrALogin = { navController.popBackStack() }
            )
        }

        composable(Rutas.MAIN) {
            val usuario = (uiState as? MainUiState.Autenticado)?.usuario
            if (usuario != null) {
                MainScreen(usuario = usuario, onCerrarSesion = viewModel::cerrarSesion)
            } else {
                // Instante entre cerrar sesión y salir hacia el login.
                Box {}
            }
        }
    }
}
